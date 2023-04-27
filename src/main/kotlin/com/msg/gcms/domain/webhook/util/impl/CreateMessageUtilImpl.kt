package com.msg.gcms.domain.webhook.util.impl

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.webhook.util.CreateMessageUtil
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class CreateMessageUtilImpl : CreateMessageUtil {

    override fun execute(clubName: String, clubType: ClubType, clubImg: String) = """
        {
          "content": "동아리 신설 요청이 들어왔어요.",
          "embeds": [
            {
              "title": "새로운 동아리가 승인을 기다리고 있어요!",
              "color": 5725911,
              "fields": [
                {
                  "name": "동아리 이름",
                  "value": "$club_name",
                  "inline": true
                },
                {
                  "name": "동아리 유형",
                  "value": "${club_type.name}",
                  "inline": true
                }
              ],
              "image": {
                "url": "$club_img"
              }
            }
          ],
          "attachments": []
        }
        """.trimIndent()
}