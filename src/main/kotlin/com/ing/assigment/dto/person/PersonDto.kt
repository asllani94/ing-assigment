package com.ing.assigment.dto.person

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class PersonDto(
    @field:Size(min = 3, max = 35) val firstName: String,
    @field:Size(min = 3, max = 35) val lastName: String,
    @field:NotNull val dateOfBirth: LocalDate,
    @field:Email val email: String,
)
