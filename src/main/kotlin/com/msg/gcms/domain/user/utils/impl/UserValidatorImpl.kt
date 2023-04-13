package com.msg.gcms.domain.user.utils.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.utils.UserValidator
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Component

@Component
class UserValidatorImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository
) : UserValidator{
    override fun validateUser(userList: List<User>, clubType: ClubType): List<User> {
        return if(clubType == ClubType.EDITORIAL) {
            verifyUser(userList)
        } else {
            verifyUser(userList)
                .filter { !verifyUserIsHead(it, clubType) }
        }
    }

    private fun verifyUser(userList: List<User>): List<User> {
        return userList
            .filter { verifyUserIsStudent(it) }
            .filter { checkUserNotSameCurrentUser(it) }
    }

    private fun verifyUserIsHead(user: User, type: ClubType): Boolean =
        clubRepository.existsByUserAndType(user, type)


    private fun verifyUserIsStudent(user: User): Boolean =
        user.roles.contains(Role.ROLE_STUDENT)


    private fun checkUserNotSameCurrentUser(user: User): Boolean {
        val currentUser = userUtil.fetchCurrentUser()

        return user.id != currentUser.id
    }
}