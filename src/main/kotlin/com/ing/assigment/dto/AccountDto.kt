package com.ing.assigment.dto

import com.ing.assigment.core.domain.AccountType
import java.time.LocalDateTime
import java.util.UUID

data class AccountDto(
    val id: UUID,
    val type: AccountType,
    val openingDate: LocalDateTime,
    val isTemporary: Boolean,
    val closureDate: LocalDateTime?,
    val initialDeposit: Double,
    val holder: PersonDto,
)
