package com.msg.gcms.domain.admin.presentation

import com.msg.gcms.domain.admin.presentation.data.request.UserDetailInfoRequest
import com.msg.gcms.domain.admin.presentation.data.response.FindAllStatisticsResponse
import com.msg.gcms.domain.admin.presentation.data.response.PendingClubResponse
import com.msg.gcms.domain.admin.service.*
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletResponse

@RequestController("/admin")
class AdminController(
    private val acceptClubService: AcceptClubService,
    private val createClubMemberExcelService: CreateClubMemberExcelService,
    private val createClubMemberExcelByClassNumService: CreateClubMemberExcelByClassNumService,
    private val clubMemberCountExcelService: ClubMemberCountExcelService,
    private val adminConverter: AdminConverter,
    private val findPendingClubListService: FindPendingClubListService,
    private val rejectClubService: RejectClubService,
    private val findAllUserListService: FindAllUserListService,
    private val userDetailInfoService: UserDetailInfoService,
    private val findAllStatisticsService: FindAllStatisticsService,
    private val clubOperationPlanHwpService: ClubOperationPlanHwpService,
    private val clubOpeningApplicationHwpService: ClubOpeningApplicationHwpService
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

    @GetMapping("/excel/club/member")
    fun getClubMemberCountExcel(@RequestParam clubType: ClubType, response: HttpServletResponse): ByteArray{
        response.setHeader("Content-Disposition", "attachment; filename=club_assign_status.xlsx")
        return clubMemberCountExcelService.execute(clubType)
    }

    @GetMapping
    fun findPendingClub(): ResponseEntity<List<PendingClubResponse>> =
        findPendingClubListService.execute()
            .map { adminConverter.toResponse(it) }
            .let { ResponseEntity.ok().body(it) }

    @PatchMapping("/{club_id}")
    fun acceptClub(@PathVariable("club_id") clubId: Long): ResponseEntity<Void> =
        acceptClubService.execute(clubId = clubId)
            .let { ResponseEntity.noContent().build() }

    @DeleteMapping("/{club_id}")
    fun rejectClub(@PathVariable("club_id") clubId: Long): ResponseEntity<Void> =
        rejectClubService.execute(clubId)
            .let { ResponseEntity.noContent().build() }

    @GetMapping("/user")
    fun findAllUser() =
            findAllUserListService.execute()
                    .map { adminConverter.toResponse(it) }
                    .let { ResponseEntity.ok().body(it) }

    @GetMapping("/user/{uuid}")
    fun userDetailInfo(@PathVariable("uuid") userDetailInfoRequest: UserDetailInfoRequest) =
            userDetailInfoService.execute(userDetailInfoRequest)
                    .let { adminConverter.toResponse(it) }
                    .let { ResponseEntity.ok().body(it) }

    @GetMapping("/club/statistics")
    fun findAllStatistics(@RequestParam clubType: ClubType): ResponseEntity<FindAllStatisticsResponse> =
        findAllStatisticsService.execute(clubType)
            .let { adminConverter.toResponse(it) }
            .let { ResponseEntity.ok().body(it) }

    @GetMapping("/hwp/operation/{club_id}")
    fun getOperationPlanToHwp(@PathVariable("club_id") clubId: Long, response: HttpServletResponse): ByteArray {
        response.setHeader("Content-Disposition", "attachment; filename=club.xlsx")
        return clubOperationPlanHwpService.execute(clubId)
    }

    @GetMapping("/hwp/application/{club_id}")
    fun getOpeningApplication(@PathVariable("club_id") clubId: Long, response: HttpServletResponse): ByteArray {
        response.setHeader("Content-Disposition", "attachment; filename=opening_application.hwp")
        return clubOpeningApplicationHwpService.execute(clubId)
    }
}