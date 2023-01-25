package com.msg.gcms.domain.club.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.joinStatus.domain.entity.JoinStatus
import com.msg.gcms.domain.user.domain.entity.User
import javax.persistence.*

@Table
@Entity
class Club(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    val name: String,

    @Column(columnDefinition="TEXT")
    val bannerImg: String,

    @Column(columnDefinition="TEXT")
    val content: String,

    @Column(columnDefinition="TEXT")
    val notionLink: String,

    val teacher: String?,

    val contact: String,

    val type: ClubType,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    val activityImg: List<ActivityImg>,

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    val joinStatus: List<JoinStatus>,
) {
}