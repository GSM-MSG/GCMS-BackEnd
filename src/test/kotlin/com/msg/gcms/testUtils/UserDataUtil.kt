package com.msg.gcms.testUtils

import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.presentaion.data.dto.SearchUserDto
import com.msg.gcms.domain.user.presentaion.data.dto.UserDto
import com.msg.gcms.domain.user.presentaion.data.response.SearchUserResponseDto
import com.msg.gcms.domain.user.presentaion.data.response.UserResponseDto
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
    fun userDto() = UserDto(
        uuid = UUID.randomUUID(),
        email = email(),
        name = nickName(),
        grade =  (1..3).random(),
        classNum = (1..3).random(),
        number = (1..3).random(),
        profileImg = profileImg(),
        clubs = listOf()
    )
    fun userDto(user: User) = UserDto(
        uuid = user.id,
        email = user.email,
        name = user.nickname,
        grade =  user.grade,
        classNum = user.classNum,
        number = user.number,
        profileImg = user.profileImg,
        clubs = listOf()
    )
    fun searchUserDto() = SearchUserDto(
        uuid = UUID.randomUUID(),
        email = email(),
        name = nickName(),
        grade =  (1..3).random(),
        classNum = (1..3).random(),
        number = (1..3).random(),
        profileImg = profileImg()
    )
    fun searchUserDto(user: User) = SearchUserDto(
        uuid = user.id,
        email = user.email,
        name = user.nickname,
        grade =  user.grade,
        classNum = user.classNum,
        number = user.number,
        profileImg = user.profileImg
    )


    fun userResponseDto(userDto: UserDto) = UserResponseDto(
        uuid = userDto.uuid,
        email = userDto.email,
        name = userDto.name,
        grade =  userDto.grade,
        classNum = userDto.classNum,
        number = userDto.number,
        profileImg = userDto.profileImg,
        clubs = listOf()
    )
    fun searchUserResponseDto(dto: SearchUserDto) = SearchUserResponseDto(
        uuid = dto.uuid,
        email = dto.email,
        name = dto.name,
        grade =  dto.grade,
        classNum = dto.classNum,
        number = dto.number,
        profileImg = dto.profileImg
    )
}