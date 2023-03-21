package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.service.CreateClubMemberExcelService
import com.msg.gcms.global.util.ExcelUtil
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class CreateClubMemberExcelServiceImpl(
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val excelUtil: ExcelUtil
) : CreateClubMemberExcelService {
    override fun execute(clubType: ClubType): ByteArray {
        val head = listOf("", "학번", "이름")
        val clubNames = mutableListOf<String>()
        val lists = mutableListOf<List<List<String>>>()
        clubRepository.findByType(clubType)
            .filter { it.clubStatus == ClubStatus.CREATED }
            .forEach { club ->
                val list = clubMemberRepository.findAllByClub(club)
                    .map { it.user }
                    .toMutableList()
                list.add(0, club.user)
                val dataLists = mutableListOf<List<String>>()
                list.forEachIndexed{index, it ->
                    dataLists.add(listOf("${index + 1}", "${it.grade}${it.classNum}${if (it.number <= 9) "0" else ""}${it.number}", it.nickname))
                }
                lists.add(dataLists)
                clubNames.add(club.name.replace(":", " "))
            }
        return excelUtil.createExcel(clubNames, head, lists)
    }
}