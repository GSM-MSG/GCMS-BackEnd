package com.msg.gcms.domain.applicant.repository

import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface ApplicantRepository : CrudRepository<Applicant, Long> {
    fun existsByUserAndClub(user: User, club: Club): Boolean
<<<<<<< HEAD

=======
    fun findAllByClub(club: Club): List<Applicant>
>>>>>>> b67230face0437b613ec4e4908b3e2220b2483d2
    @Query("select count(applicant) from Applicant applicant where applicant.club.type = :clubType and applicant.user = :user")
    fun countByClubTypeAndUser(@Param("clubType") clubType: ClubType, @Param("user") user: User): Long
    fun findByUserIdAndClub(userId: UUID, club: Club): Applicant?
}