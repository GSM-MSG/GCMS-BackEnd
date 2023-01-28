package com.msg.gcms.domain.club.controller

import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.service.FindClubListService
import com.msg.gcms.domain.club.utils.ClubConverter
import org.junit.jupiter.api.BeforeEach


class ClubControllerTest {
    private lateinit var clubConverter: ClubConverter
    private lateinit var findClubListService: FindClubListService
    private lateinit var target: ClubController

    @BeforeEach
    fun setUp() {
        clubConverter = mock()
    }
}