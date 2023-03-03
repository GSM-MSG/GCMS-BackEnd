package com.msg.gcms.domain.admin.presentation

<<<<<<< HEAD
import com.msg.gcms.domain.admin.service.AcceptClubService
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable

@RequestController("/admin")
class AdminController(
    private val acceptClubService: AcceptClubService
) {
    @PatchMapping("/{club_id}")
    fun acceptClub(@PathVariable("club_id") clubId: Long) : ResponseEntity<Void> =
        acceptClubService.execute(clubId = clubId)
            .let { ResponseEntity.noContent().build() }
=======
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
>>>>>>> 7ec78a2ea5eca09d70f268db6db4d195e4ab0f39
}