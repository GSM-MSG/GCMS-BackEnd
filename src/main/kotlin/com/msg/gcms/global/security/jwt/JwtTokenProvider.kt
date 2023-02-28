package com.msg.gcms.global.security.jwt

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.global.security.auth.AdminDetailsService
import com.msg.gcms.global.security.auth.AuthDetailsService
import com.msg.gcms.global.security.exception.ExpiredTokenException
import com.msg.gcms.global.security.exception.InvalidTokenException
import com.msg.gcms.global.security.jwt.properties.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.time.ZonedDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService,
    private val adminDetailsService: AdminDetailsService
) {
    companion object {
        const val ACCESS_TYPE = "access"
        const val REFRESH_TYPE = "refresh"
        const val ACCESS_EXP = 60L * 15 // 15 min
        const val REFRESH_EXP = 60L * 60 * 24 * 7 // 1 weeks
        const val TOKEN_PREFIX = "Bearer "
        const val AUTHORITY = "authority"
    }

    val accessExpiredTime: ZonedDateTime
        get() = ZonedDateTime.now().plusSeconds(ACCESS_EXP)

    val refreshExpiredTime: ZonedDateTime
        get() = ZonedDateTime.now().plusSeconds(REFRESH_EXP)

    fun generateAccessToken(email: String, role: Role): String =
        generateToken(email, ACCESS_TYPE, jwtProperties.accessSecret, ACCESS_EXP, role)

    fun generateRefreshToken(email: String, role: Role): String =
        generateToken(email, REFRESH_TYPE, jwtProperties.refreshSecret, REFRESH_EXP, role)

    fun resolveToken(req: HttpServletRequest): String? {
        val token = req.getHeader("Authorization") ?: return null
        return parseToken(token)
    }

    fun exactEmailFromRefreshToken(refresh: String): String {
        return getTokenSubject(refresh, jwtProperties.refreshSecret)
    }

    fun exactRoleFromRefreshToken(refresh: String): Role {
        return when (getTokenBody(refresh, jwtProperties.refreshSecret).get(AUTHORITY, String::class.java)) {
            "USER" -> Role.USER
            "ADMIN" -> Role.ADMIN
            else -> TODO("올바르지 않은 권한 입니다.")
        }

    }

    fun exactTypeFromRefreshToken(refresh: String): String =
        getTokenSubject(refresh, jwtProperties.refreshSecret)

    fun authentication(token: String): Authentication {
        val userDetails = getLoadByUserDetail(token)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun parseToken(token: String): String? =
        if (token.startsWith(TOKEN_PREFIX)) token.replace(TOKEN_PREFIX, "") else null

    fun generateToken(email: String, type: String, secret: Key, exp: Long, role: Role): String {
        val claims = Jwts.claims().setSubject(email)
        claims["type"] = type
        claims[AUTHORITY] = role
        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .signWith(secret, SignatureAlgorithm.HS256)
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .compact()
    }

    private fun getTokenBody(token: String, secret: Key): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException()
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
    }

    private fun getTokenSubject(token: String, secret: Key): String =
        getTokenBody(token, secret).subject

    private fun getLoadByUserDetail(token: String): UserDetails {
        return when (getTokenBody(token, jwtProperties.accessSecret).get(AUTHORITY, String::class.java)) {
            Role.USER.name -> authDetailsService.loadUserByUsername(getTokenSubject(token, jwtProperties.accessSecret))
            Role.ADMIN.name -> adminDetailsService.loadUserByUsername(getTokenSubject(token, jwtProperties.accessSecret))
            else -> throw TODO("유효하지 않은 토큰")
        }
    }
}