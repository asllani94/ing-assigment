package com.ing.assigment.dto.account

import com.ing.assigment.core.domain.AccountType
import com.ing.assigment.dto.person.PersonDto
import java.time.LocalDateTime
import java.util.*

data class AccountDto(
    val id: UUID,
    val type: AccountType,
    val openingDate: LocalDateTime,
    val isTemporary: Boolean,
    val closureDate: LocalDateTime?,
    val initialDeposit: Double,
    val holder: PersonDto,
)
