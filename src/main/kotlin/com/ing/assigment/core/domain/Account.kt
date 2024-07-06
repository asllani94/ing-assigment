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
) {

    init {
        require(!(isTemporary && closureDate == null)) {
            "Closure date is mandatory for temporary accounts"
        }
        require(!(isTemporary && closureDate != null && closureDate!!.isBefore(openingDate.plusMonths(2)))) {
            "Closure date cannot be earlier than the opening date + 2 months"
        }
        require(initialDeposit >= 0) {
            "Initial deposit cannot be negative"
        }
    }
}
