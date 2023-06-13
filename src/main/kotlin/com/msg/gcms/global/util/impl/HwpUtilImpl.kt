package com.msg.gcms.global.util.impl

import com.msg.gcms.global.util.HwpUtil
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.ParaText
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.tool.objectfinder.CellFinder
import org.springframework.stereotype.Component

@Component
class HwpUtilImpl: HwpUtil {
    override fun readFile(url: String): HWPFile =
        HWPReader.fromURL(url)

    override fun findCellByFieldName(hwpFile: HWPFile, fieldName: String, content: String?) {
        CellFinder.findAll(hwpFile, fieldName)
            .map {
                val para = it.paragraphList.getParagraph(0)
                para.text ?: para.createText()
                para.text.addString(content ?: "")
            }
    }
}