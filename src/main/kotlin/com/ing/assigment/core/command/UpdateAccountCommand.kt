package com.ing.assigment.core.command

import com.ing.assigment.validation.ValidClosureDate
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@ValidClosureDate
data class UpdateAccountCommand(
    @field:NotNull
    val isTemporary: Boolean,

    val closureDate: LocalDateTime?,

    @field:Min(0)
    val initialDeposit: Double,

    @field:Valid
    val holder: UpsertPersonCommand?,
)
