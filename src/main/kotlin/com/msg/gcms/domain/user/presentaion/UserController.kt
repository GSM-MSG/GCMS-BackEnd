package com.msg.gcms.domain.user.presentaion

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.presentaion.data.request.UpdateProfileImgRequestDto
import com.msg.gcms.domain.user.presentaion.data.response.SearchUserResponseDto
import com.msg.gcms.domain.user.presentaion.data.response.UserResponseDto
import com.msg.gcms.domain.user.service.FindUserService
import com.msg.gcms.domain.user.service.SearchUserService
import com.msg.gcms.domain.user.service.UpdateProfileImgService
import com.msg.gcms.domain.user.service.WithdrawUserService
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestController("/user")
class UserController(
    private val userConverter: UserConverter,
    private val findUserService: FindUserService,
    private val searchUserService: SearchUserService,
    private val updateProfileImgService: UpdateProfileImgService,
    private val withdrawUserService: WithdrawUserService
) {
    @GetMapping
    fun findUser(): ResponseEntity<UserResponseDto> {
        val result = findUserService.execute()
        val clubListResponse = result.clubs
            .map { userConverter.toResponseDto(it) }
        val response = userConverter.toResponseDto(dto = result, responseDto =  clubListResponse)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/search")
    fun searchUser(@RequestParam type: ClubType, @RequestParam("name") name: String): ResponseEntity<List<SearchUserResponseDto>> =
        userConverter.toDto(type, name)
            .let { searchUserService.execute(it) }
            .map { userConverter.toResponseDto(it) }
            .let { ResponseEntity.ok().body(it) }
    @PatchMapping
    fun updateProfileImg(@RequestBody requestDto: UpdateProfileImgRequestDto): ResponseEntity<Void> =
        userConverter.toDto(requestDto)
            .let { updateProfileImgService.execute(it) }
            .let { ResponseEntity.noContent().build() }
    @DeleteMapping
    fun withdrawUser(): ResponseEntity<Void> =
        withdrawUserService.execute()
            .let { ResponseEntity.noContent().build() }

}