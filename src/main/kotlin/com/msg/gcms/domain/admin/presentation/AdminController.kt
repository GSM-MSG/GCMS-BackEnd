package com.msg.gcms.domain.admin.presentation

import com.msg.gcms.domain.admin.presentation.data.response.PendingClubResponse
import com.msg.gcms.domain.admin.service.AcceptClubService
import com.msg.gcms.domain.admin.service.CreateClubMemberExcelByClassNumService
import com.msg.gcms.domain.admin.service.CreateClubMemberExcelService
import com.msg.gcms.domain.admin.service.FindPendingClubListService
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletResponse

@RequestController("/admin")
class AdminController(
    private val acceptClubService: AcceptClubService,
    private val createClubMemberExcelService: CreateClubMemberExcelService,
    private val createClubMemberExcelByClassNumService: CreateClubMemberExcelByClassNumService,
    private val adminConverter: AdminConverter,
    private val findPendingClubListService: FindPendingClubListService
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

    @GetMapping
    fun findPendingClub(): ResponseEntity<List<PendingClubResponse>> =
        findPendingClubListService.execute()
            .map { adminConverter.toResponse(it) }
            .let { ResponseEntity.ok().body(it) }

    @PatchMapping("/{club_id}")
    fun acceptClub(@PathVariable("club_id") clubId: Long) : ResponseEntity<Void> =
        acceptClubService.execute(clubId = clubId)
            .let { ResponseEntity.noContent().build() }
}