package com.ing.assigment.core.domain

import java.time.LocalDateTime
import java.util.UUID

data class Account(
    val id: UUID?,
    val type: AccountType,
    val openingDate: LocalDateTime,
    var isTemporary: Boolean,
    var closureDate: LocalDateTime?,
    val initialDeposit: Double,
    val holder: Person,
)
