package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.service.impl.FindClubListServiceImpl
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean

class FindClubListServiceTest : BehaviorSpec({
    @Bean
    fun clubConverter(): ClubConverter {
        return ClubConverterImpl()
    }
    val clubRepository = mockk<ClubRepository>()
    val findClubListServiceImpl = FindClubListServiceImpl(clubRepository,clubConverter())

    given("find club list") {
        val type = ClubType.values().random()
        val clubTypeDto = ClubTypeDto(type)
        val club = (1..5)
            .map { TestUtils.data().club().entity(type) }
       val clubListDto = club
           .map { ClubListDto(it.id, it.type, it.name, it.bannerImg) }


        `when`("is invoked") {
            every { clubRepository.findByType(type) } returns club
            val result = findClubListServiceImpl.execute(clubTypeDto)

            then("result should not be null") {
                result shouldNotBe null
            }

            then("clubRepository should be called") {
                verify(exactly = 1) { clubRepository.findByType(type) }
            }
            then("result should be same as clubListDto") {
                result shouldBe clubListDto
            }
        }
    }


})