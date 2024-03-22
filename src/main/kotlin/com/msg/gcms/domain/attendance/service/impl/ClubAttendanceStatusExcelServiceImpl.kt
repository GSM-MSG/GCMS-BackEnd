package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.repository.ScheduleRepository
import com.msg.gcms.domain.attendance.service.ClubAttendanceStatusExcelService
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction
import com.msg.gcms.global.util.ExcelUtil
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream
import java.time.LocalDate

@ServiceWithReadOnlyTransaction
class ClubAttendanceStatusExcelServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val attendanceRepository: AttendanceRepository


): ClubAttendanceStatusExcelService {
    override fun execute(currentDate: LocalDate): ByteArray {
        val schedule = scheduleRepository.findAllByDate(currentDate)

        val workBook = XSSFWorkbook()

        val font = workBook.createFont()
        font.fontName = "Arial"
        font.fontHeightInPoints = 11

        schedule.map {
            var rowNum = 1

            val sheet = workBook.createHeaderRow(font, it.club.name)

            val attendances = attendanceRepository.findAllBySchedule(it)

            attendances.groupBy { attendance -> attendance.user }.forEach { (user, attendances) ->
                val row = sheet.createRow(rowNum)

                val sortedAttendances = attendances.sortedBy { it.period }
                listOf(
                    "${user.grade}${user.classNum}${user.number.toString().padStart(2, '0')}",
                    user.nickname,
                    *sortedAttendances.map { sortedAttendance -> sortedAttendance.attendanceStatus.sign }.toTypedArray()
                ).forEachIndexed { idx, data ->
                    val cell = row.createCell(idx)
                    cell.setCellValue(data)
                    cell.cellStyle = workBook.createCellStyle()
                        .apply {
                            alignment = HorizontalAlignment.CENTER
                            verticalAlignment = VerticalAlignment.CENTER
                            setFont(font)
                        }
                    }
                }
                rowNum++
            }

        ByteArrayOutputStream().use { stream ->
            workBook.write(stream)
            return stream.toByteArray()
        }
    }

    // 출석부의 헤더 열을 만들고 엑셀 시트를 만들어 반환하는 함수
    private fun XSSFWorkbook.createHeaderRow(font: Font, name: String): XSSFSheet {
        val sheet = createSheet(name)
        val headerRow = sheet.createRow(0)

        listOf(
            "학번",
            "이름",
            "8교시",
            "9교시",
            "10교시",
            "11교시"
        ).forEachIndexed { idx, column ->
            val cell = headerRow.createCell(idx)
            cell.setCellValue(column)
            cell.cellStyle = createCellStyle()
                .apply {
                    fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
                    fillPattern = FillPatternType.SOLID_FOREGROUND
                    alignment = HorizontalAlignment.CENTER
                    verticalAlignment = VerticalAlignment.CENTER
                    setFont(font)
                }
        }

        return sheet
    }
}