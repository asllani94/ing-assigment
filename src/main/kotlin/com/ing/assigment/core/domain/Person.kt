package com.ing.assigment.core.domain

import java.time.LocalDate

data class Person(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String,
)
