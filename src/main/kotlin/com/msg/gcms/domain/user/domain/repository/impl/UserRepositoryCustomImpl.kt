package com.msg.gcms.domain.user.domain.repository.impl

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.entity.QUser.user
import com.msg.gcms.domain.club.domain.entity.QClub.club
import com.msg.gcms.domain.clubMember.domain.entity.QClubMember.clubMember
import com.msg.gcms.domain.user.domain.repository.UserRepositoryCustom
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : UserRepositoryCustom {
    override fun findUserNotJoin(type: ClubType, name: String): List<User> =
        queryFactory.selectFrom(user)
            .where(JPAExpressions.selectFrom(club).innerJoin(clubMember).on(club.type.eq(type).and(club.eq(clubMember.club))).where(user.id.eq(
                clubMember.user.id)).notExists())
            .fetch()

}