package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.service.CreateClubMemberExcelByClassNumService
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.util.ExcelUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class CreateClubMemberExcelByClassNumServiceImpl(
    private val userRepository: UserRepository,
    private val excelUtil: ExcelUtil
) : CreateClubMemberExcelByClassNumService {
    override fun execute(clubType: ClubType): ByteArray {
        val gradeClass = mutableListOf<String>()
        val head = listOf("번호", "성명", "부서명", "부서반", "담당교사")
        val dataMap = mutableMapOf<String, MutableList<MutableList<String>>>()
        val list = userRepository.findAll()
            .filter { it.roles.contains(Role.ROLE_STUDENT) }
            .toMutableList()
        list.sortWith(compareBy<User> {it.grade}.thenBy { it.classNum }.thenBy { it.number })
        list.forEach {
            if (!gradeClass.contains("${it.grade}-${it.classNum}")) {
                gradeClass.add("${it.grade}-${it.classNum}")
                dataMap["${it.grade}-${it.classNum}"] = mutableListOf()
            }
            val findClub = findClub(it, clubType)
            dataMap["${it.grade}-${it.classNum}"]!!.add(mutableListOf(it.number.toString(),it.nickname, findClub.name, "", findClub.teacher?:""))
        }
        return excelUtil.createExcel(gradeClass, head, dataMap)
    }

    private fun findClub(it: User, clubType: ClubType): Club{
        val dummy = (it.clubMember.find { it.club.type == clubType && it.club.clubStatus == ClubStatus.CREATED}
            ?: ClubMember(
                0,
                Club(
                    name = "소속없음",
                    bannerImg = "",
                    content = "",
                    notionLink = "",
                    teacher = null,
                    contact = "",
                    isOpened = false,
                    user = it,
                    type = clubType
                ),
                it
            ))
        return it.club.find { it.type == clubType && it.clubStatus == ClubStatus.CREATED}
            ?: dummy.club
    }
}