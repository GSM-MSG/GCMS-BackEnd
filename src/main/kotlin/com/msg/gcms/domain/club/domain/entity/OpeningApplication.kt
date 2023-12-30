package com.msg.gcms.domain.club.domain.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class OpeningApplication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // 연구 분야
    val field: String,

    //연구 주제
    val subject: String,

    // 연구 분야 및 주제 선정 이유
    val reason: String,

    // 연구 활동 목표
    val target: String,

    // 기대 효과
    val effect: String
)