package com.msg.gcms.domain.admin.presentation

import com.msg.gcms.domain.admin.service.CreateClubMemberExcelByClassNumService
import com.msg.gcms.domain.admin.service.CreateClubMemberExcelService
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.global.annotation.RequestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletResponse

@RequestController("/admin")
class AdminController(
    private val createClubMemberExcelService: CreateClubMemberExcelService,
    private val createClubMemberExcelByClassNumService: CreateClubMemberExcelByClassNumService
) {
    @GetMapping("/excel/club")
    fun getClubMemberExcelByClub(@RequestParam clubType: ClubType, response: HttpServletResponse): ByteArray{
        response.setHeader("Content-Disposition", "attachment; filename=club.xlsx")
        return createClubMemberExcelService.execute(clubType)
    }

    @GetMapping("/excel/club/grade")
    fun getClubMemberExcelByGrade(@RequestParam clubType: ClubType, response: HttpServletResponse): ByteArray{
        response.setHeader("Content-Disposition", "attachment; filename=club_assign_status.xlsx")
        return createClubMemberExcelByClassNumService.execute(clubType)
    }
}