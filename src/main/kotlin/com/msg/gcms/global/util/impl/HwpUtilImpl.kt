package com.msg.gcms.global.util.impl

import com.msg.gcms.global.util.HwpUtil
import com.msg.gcms.global.util.properties.HwpProperties
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.tool.objectfinder.CellFinder
import org.springframework.stereotype.Component

@Component
class HwpUtilImpl(
    private val hwpProperties: HwpProperties
) : HwpUtil {
    override fun readOperationPlan(): HWPFile =
        HWPReader.fromURL(hwpProperties.operationPlanUrl)

    override fun readOpeningApplication(): HWPFile =
        HWPReader.fromURL(hwpProperties.openingApplicationUrl)

    override fun insertByFieldName(hwpFile: HWPFile, fieldName: String, content: String?) {
        CellFinder.findAll(hwpFile, fieldName)
            .map {
                val para = it.paragraphList.getParagraph(0)
                para.text ?: para.createText()
                para.text.addString(content ?: "")
            }
    }
}