package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.service.DownloadClubOpeningApplicationHwpService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.util.HwpUtil
import com.msg.gcms.main
import kr.dogfoot.hwplib.writer.HWPWriter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayOutputStream

@Service
@Transactional(readOnly = true)
class DownloadClubOpeningApplicationHwpServiceImpl(
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

        val (new, maintain) =
            if(club.clubStatus == ClubStatus.PENDING)
                    ("신설[ v ]" to "유지[  ]")
            else
                    ("신설[  ]" to "유지[ v ]")

        mapOf(
            Pair("신청인 정보", "학번 및 성명 : $clubOwnerInfo ${clubOwner.nickname}"),
            Pair("신청 형태 신설", new),
            Pair("신청 형태 유지", maintain),
            Pair("동아리명", club.name),
            Pair("연구 분야", openingApplication.field),
            Pair("연구 주제", openingApplication.subject),
            Pair("선정 이유", openingApplication.reason),
            Pair("연구 활동 목표", openingApplication.subject),
            Pair("학번1", clubOwnerInfo),
            Pair("이름1", clubOwner.nickname),
            Pair("역할1", "부장"),
            Pair("기대 효과", openingApplication.effect),
            *(2..club.clubMember.size).map {sequence ->
                    Pair("학번$sequence", getStudentInfo(club.clubMember[sequence - 1].user))
                    Pair("이름$sequence", club.clubMember[sequence - 1].user.nickname)
                    Pair("역할$sequence", "")
            }.toTypedArray()
        ).forEach {
            hwpUtil.insertByFieldName(openingApplicationForm, it.key, it.value)
        }

        val outPutStream = ByteArrayOutputStream()

        HWPWriter.toStream(openingApplicationForm, outPutStream)

        return outPutStream.toByteArray()
    }

    private fun getStudentInfo(user: User): String =
        "${user.grade}${user.classNum}${String.format("%02d", user.number)}"
}