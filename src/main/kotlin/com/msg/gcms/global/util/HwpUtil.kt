package com.msg.gcms.global.util

import kr.dogfoot.hwplib.`object`.HWPFile

interface HwpUtil {
    fun readOperationPlan(): HWPFile
    fun readOpeningApplication(): HWPFile

    fun insertByFieldName(hwpFile: HWPFile, fieldName: String, content: String?)
}