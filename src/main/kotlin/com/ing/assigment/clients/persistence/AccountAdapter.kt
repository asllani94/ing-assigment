package com.ing.assigment.clients.persistence

import com.ing.assigment.core.domain.Account
import com.ing.assigment.core.domain.Person
import com.ing.assigment.core.port.AccountPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountAdapter(
    private val accountRepository: AccountRepository,
) : AccountPort {

    override fun createAccount(account: Account): Account {
        return accountRepository.save(account.toEntity()).toDomain()
    }

    override fun findAccount(accountId: UUID): Account? {
        return accountRepository
            .findById(accountId)
            .orElse(null)
            ?.toDomain()
    }

    override fun updateAccount(accountId: UUID, account: Account): Account {
        return accountRepository.save(account.toEntity()).toDomain()
    }

    override fun deleteAccount(accountId: UUID) {
        accountRepository.deleteById(accountId)
    }
}

fun Account.toEntity(): AccountEntity {
    return AccountEntity(
        id = id,
        type = this.type,
        openingDate = this.openingDate,
        isTemporary = this.isTemporary,
        closureDate = this.closureDate,
        initialDeposit = this.initialDeposit,
        holder = this.holder.toEntity(),
    )
}

fun Person.toEntity(): PersonEntity {
    return PersonEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        dateOfBirth = this.dateOfBirth,
        email = this.email,
    )
}

fun AccountEntity.toDomain(): Account {
    return Account(
        id = id,
        type = this.type,
        openingDate = this.openingDate,
        isTemporary = this.isTemporary,
        closureDate = this.closureDate,
        initialDeposit = this.initialDeposit,
        holder = this.holder!!.toDomain(),
    )
}

fun PersonEntity.toDomain(): Person {
    return Person(
        firstName = firstName,
        lastName = lastName,
        dateOfBirth = dateOfBirth,
        email = email,
    )
}
