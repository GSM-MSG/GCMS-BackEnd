package com.msg.gcms.domain.user.domain.repository

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserRepository: CrudRepository<User, UUID> {
    fun findByEmail(email: String) : User?
    @Query(value = "select u from User u where not exists "+
            "(select c from Club c inner join ClubMember cm on c.type = :type and c = cm.club where u.id = cm.user.id) "+"and u.nickname like %:nickname%")
    fun findUserNotJoin(@Param("type")clubType: ClubType, @Param("nickname")name: String): List<User>
    fun findByNicknameContaining(name: String): List<User>
}