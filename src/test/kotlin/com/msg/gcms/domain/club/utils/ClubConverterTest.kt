package com.msg.gcms.domain.club.utils

import com.msg.gcms.domain.club.domain.entity.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.context.annotation.Bean

class ClubConverterTest : BehaviorSpec({
    @Bean
    fun clubConverter(): ClubConverter {
        return ClubConverterImpl()
    }

    given("convert toClubTyDto request") {
        val type = ClubType.values().random()
        val clubTypeDto = TestUtils.data().club().clubTypeDto(type)

        `when`("is converting clubType") {
            val convertedDto = clubConverter().toDto(type)
            then("convertedDto should be as same clubTypeDto") {
                convertedDto.clubType shouldBe clubTypeDto.clubType
            }
        }
    }

    given("convert toClubListResponseDto request") {
        val type = ClubType.values().random()
        val clubListDto = (1..5)
            .map { ClubListDto(it.toLong(), type, "동아리", "동아리 사진") }
        val responseDto = clubListDto
            .map { ClubListResponseDto(it.id, type, it.name, it.bannerImg) }

        `when`("is converting clubListDto") {
            val convertedDto = clubListDto.map { clubConverter().toResponseDto(it) }
            then("convertedDto should be as same responseDto") {
                convertedDto shouldBe responseDto
            }
        }
    }

    given("convert toClubListDto request") {
        val type = ClubType.values().random()
        val club = (1..5).map {
            TestUtils.data().club().entity(type)
        }
        val clubListDto = club
            .map { ClubListDto(it.id, type, it.name, it.bannerImg) }

        `when`("is converting clubListDto") {
            val convertedDto = club.map { clubConverter().toDto(it) }
            then("convertedDto should be as same responseDto") {
                convertedDto shouldBe clubListDto
            }
        }
    }
})