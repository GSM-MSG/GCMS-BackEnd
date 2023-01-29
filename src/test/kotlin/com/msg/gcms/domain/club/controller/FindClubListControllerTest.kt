package com.msg.gcms.domain.club.controller

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.service.FindClubListService
import com.msg.gcms.domain.club.service.impl.FindClubListServiceImpl
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus


class FindClubListControllerTest: BehaviorSpec({
    @Bean
    fun clubConverter(): ClubConverter {
        return ClubConverterImpl()
    }
    val findClubListService = mockk<FindClubListService>()
    val clubController = ClubController(clubConverter(), findClubListService)

    given("find club list request") {
        val type = ClubType.valueOf("MAJOR")
        val clubTypeDto = ClubTypeDto(type)
        val clubListDto = (1..5).map { ClubListDto(it.toLong(), type,"동아리","동아리 사진") }
        val responseDto = (1..5).map { ClubListResponseDto(it.toLong(), type,"동아리","동아리 사진") }
        `when`("is converting clubType") {
            val convertedDto = clubConverter().toDto(type)
            then("convertedDto should be as same clubTypeDto") {
                convertedDto.clubType shouldBe clubTypeDto.clubType
            }
        }

        `when`("is received") {
            every { findClubListService.execute(clubTypeDto) } returns clubListDto
            val response = clubController.findClubListByClubType(type)
            val body = response.body
            then("response body should not be null") {
                response.body shouldNotBe null
            }

            then("business logic in findClubListService should be called") {
                verify(exactly = 1) { findClubListService.execute(clubTypeDto) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
            then("result should be same ad responseDto") {
                body shouldBe responseDto
            }
        }
        `when`("is converting clubListDto") {
            val convertedDto = clubListDto.map { clubConverter().toResponseDto(it) }
            then("convertedDto should be as same responseDto") {
                convertedDto shouldBe responseDto
            }
        }
    }
})