package com.msg.gcms.domain.notice.domain.entity

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Notice (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(columnDefinition = "VARCHAR(100)", length = 100)
    val title: String,

    @Column(columnDefinition = "TEXT", length = 2000)
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    val club: Club,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @CreatedDate
    @Column(columnDefinition = "DATETIME(6)")
    val createdAt: LocalDateTime
)