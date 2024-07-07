package com.ing.assigment.dto

import java.time.LocalDate

data class PersonDto(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String,
)
