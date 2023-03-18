package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.service.ClubMemberCountExcelService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.global.util.ExcelUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class ClubMemberCountExcelServiceImpl(
    private val clubRepository: ClubRepository,
    private val excelUtil: ExcelUtil,
) : ClubMemberCountExcelService{
    override fun execute(clubType: ClubType): ByteArray {
        val header = listOf("동아리 이름", "동아리 담당 선생님", "동아리 인원수")
        val dataLists = clubRepository.findByType(clubType)
            .map { listOf(it.name, it.teacher ?: "", "${it.clubMember.size + 1}") }
        return excelUtil.createExcel("동아리 인원수", header, dataLists)
    }
}