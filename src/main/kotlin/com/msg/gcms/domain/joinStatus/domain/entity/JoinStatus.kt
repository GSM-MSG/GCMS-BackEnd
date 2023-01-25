package com.msg.gcms.domain.joinStatus.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.user.domain.entity.User
import javax.persistence.*

@Table
@Entity
class JoinStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    val club: Club,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) {
}