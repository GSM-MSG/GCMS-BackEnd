package com.msg.gcms.global.config.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class QuerydslConfig(
    @PersistenceContext
    val entityManager: EntityManager
) {
    @Bean
    fun jpaQueryFactory() =
        JPAQueryFactory(entityManager)
}