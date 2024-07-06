package com.ing.assigment.core.command

import java.time.LocalDateTime

data class UpdateAccountCommand(
    val isTemporary: Boolean,

    val closureDate: LocalDateTime?,

    val initialDeposit: Double,

    val holder: UpsertPersonCommand?,
)
