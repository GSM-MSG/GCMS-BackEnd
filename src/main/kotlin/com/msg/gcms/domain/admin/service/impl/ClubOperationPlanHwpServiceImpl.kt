package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.service.ClubOperationPlanHwpService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.domain.repository.OperationPlanRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.OperationPlanNotFoundException
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.util.HwpUtil
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
    private val hwpUtil: HwpUtil
) : ClubOperationPlanHwpService {
    override fun execute(clubId: Long): ByteArray {
        val operationPlanForm = hwpUtil.readOperationPlan()

        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()

        val operationPlan = operationPlanRepository.findByIdOrNull(clubId)
            ?: throw OperationPlanNotFoundException()

        val user: User = club.user

        hwpUtil.insertByFieldName(operationPlanForm, "분야", operationPlan.field)
        hwpUtil.insertByFieldName(operationPlanForm, "동아리 장소", operationPlan.place)
        hwpUtil.insertByFieldName(operationPlanForm, "동아리명", club.name)
        hwpUtil.insertByFieldName(operationPlanForm, "지도교사", club.teacher)

        hwpUtil.insertByFieldName(operationPlanForm, "학번 이름1", sortNameAndNum(user))
        hwpUtil.insertByFieldName(operationPlanForm, "담당역할1", "부장")

        for(i in 2..club.clubMember.size) {
            hwpUtil.insertByFieldName(operationPlanForm, "학번 이름${i}", sortNameAndNum(club.clubMember[i - 2].user))
            hwpUtil.insertByFieldName(operationPlanForm, "담당역할${i}", "")
        }

        hwpUtil.insertByFieldName(operationPlanForm, "연구주제", operationPlan.subject)
        hwpUtil.insertByFieldName(operationPlanForm, "연구내용", operationPlan.content)
        hwpUtil.insertByFieldName(operationPlanForm, "동아리 운영 규칙", operationPlan.rule)


        operationPlan.monthlyPlan
            .map {
                hwpUtil.insertByFieldName(operationPlanForm, "${it.month}월 개요", it.summaryPlan)
                hwpUtil.insertByFieldName(operationPlanForm, "${it.month}월 연구 내용", it.plan)
        }

        val outPutStream = ByteArrayOutputStream()

        HWPWriter.toStream(operationPlanForm, outPutStream)
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