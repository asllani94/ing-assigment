package com.ing.assigment.core.command

import com.ing.assigment.core.domain.AccountType
import java.time.LocalDateTime

data class CreateAccountCommand(
    val type: AccountType,
    val openingDate: LocalDateTime,
    val isTemporary: Boolean,
    val closureDate: LocalDateTime?,
    val initialDeposit: Double,
    val holder: UpsertPersonCommand,
)
