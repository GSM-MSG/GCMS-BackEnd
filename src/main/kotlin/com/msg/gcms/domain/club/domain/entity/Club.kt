package com.msg.gcms.domain.club.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.user.domain.entity.User
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
class Club(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    val name: String,

    @Column(columnDefinition = "TEXT")
    val bannerImg: String,

    @Column(columnDefinition = "TEXT")
    val content: String,

    @Column(columnDefinition = "TEXT")
    val notionLink: String,

    val teacher: String?,

    val contact: String,

    val type: ClubType,

    val isOpened: Boolean,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club", cascade = [CascadeType.REMOVE])
    val activityImg: List<ActivityImg> = listOf(),

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club", cascade = [CascadeType.REMOVE])
    val applicant: List<Applicant> = listOf(),

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club", cascade = [CascadeType.REMOVE])
    val clubMember: List<ClubMember> = listOf(),

    @Enumerated(value = EnumType.STRING)
    val clubStatus: ClubStatus = ClubStatus.PENDING,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "club")
    @MapsId
    var operationPlan: OperationPlan? = null
)