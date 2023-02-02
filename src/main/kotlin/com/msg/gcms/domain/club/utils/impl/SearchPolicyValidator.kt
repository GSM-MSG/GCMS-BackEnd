package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.utils.SearchPolicyValidator
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class SearchPolicyValidator(
    val clubRepository: ClubRepository
) : SearchPolicyValidator {
    override fun validate(user: User): Boolean =
        clubRepository.existsByUser(user)
}