package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.service.DownloadClubOpeningApplicationHwpService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.util.HwpUtil
import kr.dogfoot.hwplib.writer.HWPWriter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayOutputStream

@Service
@Transactional(readOnly = true)
class DownloadDownloadClubOpeningApplicationHwpServiceImpl(
    private val clubRepository: ClubRepository,
    private val hwpUtil: HwpUtil
) : DownloadClubOpeningApplicationHwpService {
    override fun execute(clubId: Long): ByteArray {
        val openingApplicationForm = hwpUtil.readOpeningApplication()

        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()

        val clubOwner = club.user

        val openingApplication = club.openingApplication ?: throw Exception()

        val clubOwnerInfo = getStudentInfo(clubOwner)

        hwpUtil.insertByFieldName(openingApplicationForm, "신청인 정보", "학번 및 성명 : $clubOwnerInfo ${clubOwner.nickname}")

        val (new, maintain) =
            if(club.clubStatus == ClubStatus.PENDING)
                    ("신설[ v ]" to "유지[  ]")
            else
                    ("신설[  ]" to "유지[ v ]")

        hwpUtil.insertByFieldName(openingApplicationForm, "신청 형태 신설", new)
        hwpUtil.insertByFieldName(openingApplicationForm, "신청 형태 유지", maintain)

        hwpUtil.insertByFieldName(openingApplicationForm, "동아리명", club.name)
        hwpUtil.insertByFieldName(openingApplicationForm, "연구 분야", openingApplication.field)
        hwpUtil.insertByFieldName(openingApplicationForm, "연구 주제", openingApplication.subject)
        hwpUtil.insertByFieldName(openingApplicationForm, "선정 이유", openingApplication.reason)
        hwpUtil.insertByFieldName(openingApplicationForm, "연구 활동 목표", openingApplication.subject)

        hwpUtil.insertByFieldName(openingApplicationForm, "학번1", clubOwnerInfo)
        hwpUtil.insertByFieldName(openingApplicationForm, "이름1", clubOwner.nickname)
        hwpUtil.insertByFieldName(openingApplicationForm, "역할1", "부장")

        for (i in 2..club.clubMember.size) {
            hwpUtil.insertByFieldName(openingApplicationForm, "학번$i", getStudentInfo(club.clubMember[i].user))
            hwpUtil.insertByFieldName(openingApplicationForm, "이름$i", club.clubMember[i].user.nickname)
            hwpUtil.insertByFieldName(openingApplicationForm, "역할$i", "")
        }

        hwpUtil.insertByFieldName(openingApplicationForm, "기대 효과", openingApplication.effect)

        val outPutStream = ByteArrayOutputStream()

        HWPWriter.toStream(openingApplicationForm, outPutStream)

        return outPutStream.toByteArray()
    }

    private fun getStudentInfo(user: User): String =
        "${user.grade}${user.classNum}${String.format("%02d", user.number)}"
}