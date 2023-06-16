package com.msg.gcms.global.util

import kr.dogfoot.hwplib.`object`.HWPFile

interface HwpUtil {
    fun readFile(url: String): HWPFile

    fun insertByFieldName(hwpFile: HWPFile, fieldName: String, content: String?)
}