package com.ing.assigment.dto.account

import com.ing.assigment.core.domain.AccountType
import com.ing.assigment.dto.person.PersonDto
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CreateAccountDto(
    @field:NotNull val type: AccountType,
    @field:NotNull val openingDate: LocalDateTime,
    @field:NotNull val isTemporary: Boolean,
    val closureDate: LocalDateTime?,
    @field:Min(0) val initialDeposit: Double,
    @field:NotNull val holder: PersonDto,
)
