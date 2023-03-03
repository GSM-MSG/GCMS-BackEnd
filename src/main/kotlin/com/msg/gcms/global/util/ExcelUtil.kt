package com.msg.gcms.global.util

interface ExcelUtil {
    fun createExcel(attributes: List<String>, dataLists: List<List<String>>): ByteArray
    fun createExcel(header: String, attributes: List<String>, dataLists: List<List<String>>): ByteArray
    fun createExcel(headers: List<String>, attributes: List<String>, dataLists: List<List<List<String>>>): ByteArray
}