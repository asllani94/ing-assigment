package com.ing.assigment.testsupport.dsl

import com.ing.assigment.core.domain.Account
import com.ing.assigment.core.domain.AccountType
import com.ing.assigment.core.domain.Person
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID.randomUUID

@DslMarker
annotation class AccountDsl

fun account(block: AccountBuilder.() -> Unit): Account = AccountBuilder().apply(block).build()

fun person(block: PersonBuilder.() -> Unit): Person = PersonBuilder().apply(block).build()

@AccountDsl
class AccountBuilder {
    var id: UUID? = randomUUID()
    var type: AccountType = AccountType.CURRENT
    var openingDate: LocalDateTime = LocalDateTime.now()
    var isTemporary: Boolean = true
    var closureDate: LocalDateTime? = openingDate.plusMonths(3)
    var initialDeposit: Double = 100.0
    var holder: Person = person { }

    fun build() = Account(
        id = id,
        type = type,
        openingDate = openingDate,
        isTemporary = isTemporary,
        closureDate = closureDate,
        initialDeposit = initialDeposit,
        holder = holder,
    )
}

@AccountDsl
class PersonBuilder {
    var firstName: String = "John"
    var lastName: String = "Doe"
    var dateOfBirth: LocalDate = LocalDate.now().minusYears(30)
    var email: String = "johndoe@example.com"

    fun build() = Person(
        firstName = firstName,
        lastName = lastName,
        dateOfBirth = dateOfBirth,
        email = email,
    )
}
