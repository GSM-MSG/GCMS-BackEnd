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

    val field: String,

    val subject: String,

    val reason: String,

    val target: String,

    val effect: String
)