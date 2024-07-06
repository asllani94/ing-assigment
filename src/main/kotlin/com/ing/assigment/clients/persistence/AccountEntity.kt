package com.ing.assigment.clients.persistence
import com.ing.assigment.core.domain.AccountType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("ACCOUNTS")
data class AccountEntity(
    @Id var id: UUID?,
    val type: AccountType,
    val openingDate: LocalDateTime,
    var isTemporary: Boolean,
    var closureDate: LocalDateTime?,
    val initialDeposit: Double,
    val holder: PersonEntity?,
)
