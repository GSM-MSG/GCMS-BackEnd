package com.msg.gcms.global.util.impl

import com.msg.gcms.global.util.ExcelUtil
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream

@Component
class ExcelUtilImpl : ExcelUtil {
    override fun createExcel(attributes: List<String>, datasList: List<List<String>>): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet()

        val headerRow = sheet.createRow(0)
        insertDataListsAtRow(headerRow, attributes, getHeaderCellStyle(workbook))

        datasList.forEachIndexed { idx, dataLists ->
            val row = sheet.createRow(idx + 1)
            insertDataListsAtRow(row, dataLists, getDefaultCellStyle(workbook))
        }

        ByteArrayOutputStream().use { stream ->
            workbook.write(stream)
            return stream.toByteArray()
        }
    }

    override fun createExcel(header: String, attributes: List<String>, datasLists: List<List<String>>): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet(header)
        val headerRow = sheet.createRow(0)
        insertDataListsAtRow(headerRow, attributes, getHeaderCellStyle(workbook))

        datasLists.forEachIndexed { idx, dataLists ->
            val row = sheet.createRow(idx + 1)
            insertDataListsAtRow(row, dataLists, getDefaultCellStyle(workbook))
        }

        ByteArrayOutputStream().use { stream ->
            workbook.write(stream)
            return stream.toByteArray()
        }
    }

    override fun createExcel(
        headers: List<String>,
        attributes: List<String>,
        dataLists: List<List<List<String>>>
    ): ByteArray {
        val workbook = XSSFWorkbook()
        headers.forEachIndexed { index, header ->
            val sheet = workbook.createSheet(header)
            val headerRow = sheet.createRow(0)
            insertDataListsAtRow(headerRow, attributes, getHeaderCellStyle(workbook))
            dataLists[index].forEachIndexed { idx, dataLists ->
                val row = sheet.createRow(idx + 1)
                insertDataListsAtRow(row, dataLists, getDefaultCellStyle(workbook))
            }
        }
        ByteArrayOutputStream().use { stream ->
            workbook.write(stream)
            return stream.toByteArray()
        }
    }

    private fun insertDataListsAtRow(
        headerRow: XSSFRow,
        attributes: List<String?>,
        style: XSSFCellStyle
    ) {
        attributes.forEachIndexed { index, text ->
            val cell = headerRow.createCell(index)
            cell.setCellValue(text)
            cell.cellStyle = style
        }
    }

    private fun getHeaderCellStyle(workbook: XSSFWorkbook): XSSFCellStyle =
        workbook.createCellStyle()
            .apply {
                fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
                fillPattern = FillPatternType.SOLID_FOREGROUND
                alignment = HorizontalAlignment.LEFT
                verticalAlignment = VerticalAlignment.CENTER
            }

    private fun getDefaultCellStyle(workbook: XSSFWorkbook): XSSFCellStyle =
        workbook.createCellStyle()
            .apply {
                alignment = HorizontalAlignment.LEFT
                verticalAlignment = VerticalAlignment.CENTER
            }

}