package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.service.ClubOperationPlanHwpService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.domain.repository.OperationPlanRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.OperationPlanNotFoundException
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.util.HwpUtil
import com.msg.gcms.global.util.properties.HwpProperties
import kr.dogfoot.hwplib.writer.HWPWriter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayOutputStream

@Service
@Transactional(readOnly = true)
class ClubOperationPlanHwpServiceImpl(
    private val clubRepository: ClubRepository,
    private val operationPlanRepository: OperationPlanRepository,
    private val hwpProperties: HwpProperties,
    private val hwpUtil: HwpUtil
): ClubOperationPlanHwpService {
    override fun execute(clubId: Long): ByteArray {
        val hwpUrl = hwpProperties.url
        val hwpFile = hwpUtil.readFile(hwpUrl)

        val club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()
        val operationPlan = operationPlanRepository.findByIdOrNull(clubId) ?: throw OperationPlanNotFoundException()
        val user: User = club.user

        hwpUtil.insertByFieldName(hwpFile, "분야", operationPlan.field)
        hwpUtil.insertByFieldName(hwpFile, "동아리 장소", operationPlan.place)
        hwpUtil.insertByFieldName(hwpFile, "동아리명", club.name)
        hwpUtil.insertByFieldName(hwpFile, "지도교사", club.teacher)

        hwpUtil.insertByFieldName(hwpFile, "학번 이름1", sortNameAndNum(user))
        hwpUtil.insertByFieldName(hwpFile, "담당역할1", "부장")

        for(i in 2..12) {
            val member = club.clubMember[i - 2].user
            hwpUtil.insertByFieldName(hwpFile, "학번 이름${i}", sortNameAndNum(member))
            hwpUtil.insertByFieldName(hwpFile, "담당역할${i}", "")
        }

        hwpUtil.insertByFieldName(hwpFile, "연구주제", operationPlan.subject)
        hwpUtil.insertByFieldName(hwpFile, "연구내용", operationPlan.content)
        hwpUtil.insertByFieldName(hwpFile, "동아리 운영 규칙", operationPlan.rule)


        operationPlan.monthlyPlan
            .map {
                hwpUtil.insertByFieldName(hwpFile, "${it.month}월 개요", it.summaryPlan)
                hwpUtil.insertByFieldName(hwpFile, "${it.month}월 연구 내용", it.plan)
        }

        val outPutStream = ByteArrayOutputStream()

        HWPWriter.toStream(hwpFile, outPutStream)
        return outPutStream.toByteArray()
    }

    private fun sortNameAndNum(user: User): String =
        "${user.nickname}${user.grade}${user.classNum}${
            when(user.number) {
                in 1..10 -> "0${user.number}"
                else -> "${user.number}"
            }
        }"
}