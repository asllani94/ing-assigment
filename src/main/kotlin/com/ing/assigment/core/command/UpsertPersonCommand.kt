package com.ing.assigment.core.command
import com.ing.assigment.validation.AgeLimit
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class UpsertPersonCommand(
    @Size(min = 3, max = 35)
    val firstName: String,

    @Size(min = 3, max = 35)
    val lastName: String,

    @NotNull
    @AgeLimit
    val dateOfBirth: LocalDate,

    @Email
    val email: String,
)
