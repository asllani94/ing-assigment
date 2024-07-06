package com.ing.assigment.dto.account

import com.ing.assigment.dto.person.PersonDto
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class UpdateAccountDto(
    @field:NotNull
    val isTemporary: Boolean,

    val closureDate: LocalDateTime?,

    @field:Min(0)
    val initialDeposit: Double,

    val holder: PersonDto?,
)
