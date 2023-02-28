package com.msg.gcms.domain.admin.domain.entity

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Admin(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID,

    @Column(length = 20)
    val email: String,
) {
}