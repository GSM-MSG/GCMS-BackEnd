package com.msg.gcms.domain.user.presentaion

import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.user.presentaion.data.response.UserResponseDto
import com.msg.gcms.domain.user.service.FindUserService
import com.msg.gcms.domain.user.utils.UserConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val clubConverter: ClubConverter,
    private val userConverter: UserConverter,
    private val findUserService: FindUserService
) {
    @GetMapping
    fun findUser(): ResponseEntity<UserResponseDto> {
        val result = findUserService.execute()
        val clubListResponse = result.clubs.map { clubConverter.toResponseDto(it) }
        val response = userConverter.toResponseDto(dto = result, clubListResponse)
        return ResponseEntity.ok().body(response)
    }
}