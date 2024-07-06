package com.ing.assigment.clients.persistence

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("PERSONS")
data class PersonEntity(
    val firstName: String,
    val lastName: String,
    var dateOfBirth: LocalDate,
    var email: String,
)
