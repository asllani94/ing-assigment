package com.ing.assigment.core.command
import java.time.LocalDate

data class UpsertPersonCommand(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String,
)
