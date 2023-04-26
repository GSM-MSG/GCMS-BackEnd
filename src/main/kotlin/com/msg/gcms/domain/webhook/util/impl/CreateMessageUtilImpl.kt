package com.msg.gcms.domain.webhook.util.impl

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.webhook.util.CreateMessageUtil
import org.springframework.stereotype.Service

@Service
class CreateMessageUtilImpl : CreateMessageUtil {

    override fun execute(club_name: String, club_type: ClubType, club_img: String) = """
        {
          "content": null,
          "embeds": [
            {
              "title": "띵동!",
              "description": "새로운 동아리 신설 요청이 들어왔어요.",
              "color": 4097741,
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
          "username": "띵동이",
          "attachments": []
        }
        """.trimIndent()
}