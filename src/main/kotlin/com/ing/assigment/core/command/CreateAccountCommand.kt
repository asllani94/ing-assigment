package com.ing.assigment.core.command

import com.ing.assigment.core.domain.AccountType
import com.ing.assigment.validation.ValidClosureDate
import com.ing.assigment.validation.ValidOpeningDate
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.time.LocalDateTime

@ValidClosureDate
data class CreateAccountCommand(
    @field:NotNull
    val type: AccountType,

    @field:NotNull
    @field:ValidOpeningDate
    val openingDate: LocalDateTime,

    @field:NotNull
    val isTemporary: Boolean,

    val closureDate: LocalDateTime?,

    @field:PositiveOrZero
    val initialDeposit: Double,

    @field:NotNull
    @field:Valid
    val holder: UpsertPersonCommand,
)
