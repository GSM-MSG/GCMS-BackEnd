package com.msg.gcms.global.annotation

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Service
@Transactional(rollbackFor = [Exception::class])
annotation class ServiceWithTransaction()
