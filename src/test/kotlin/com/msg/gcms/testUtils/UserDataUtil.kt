package com.msg.gcms.testUtils

import com.msg.gcms.domain.user.domain.entity.User
import java.util.*

object UserDataUtil {
    private fun email() = listOf("email", "email1" ,"email2").random()
    private fun nickName() = listOf("이름" , "이름1", "이름2").random()
    private fun profileImg() = listOf("유저이미지", "유저이미지1").random()

    fun entity() = User(
        id = UUID.randomUUID(),
        email = email(),
        nickname = nickName(),
        profileImg = profileImg(),
        grade = (1..3).random(),
        classNum = (1..3).random(),
        number = (1..3).random(),
        club = listOf(),
        clubMember = listOf(),
        applicant = listOf()
    )
}