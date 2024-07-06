package com.ing.assigment.core.service

import com.ing.assigment.core.command.CreateAccountCommand
import com.ing.assigment.core.command.UpdateAccountCommand
import com.ing.assigment.core.command.UpsertPersonCommand
import com.ing.assigment.core.domain.Account
import com.ing.assigment.core.domain.Person
import com.ing.assigment.core.port.AccountPort
import com.ing.assigment.errorhandling.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AccountService(
    private val accountPort: AccountPort,
) : IAccountService {

    @Transactional
    override fun createAccount(createAccountCommand: CreateAccountCommand): Account {
        val account = createAccountCommand.toDomain()

        return accountPort.createAccount(account)
    }

    @Transactional(readOnly = true)
    override fun getAccount(accountUuid: UUID): Account? =
        accountPort.findAccount(accountUuid)

    @Transactional
    override fun updateAccount(accountUuid: UUID, updateCommand: UpdateAccountCommand): Account =
        accountPort.findAccount(accountUuid)?.let { currentAccount ->

            val accountToUpdate = currentAccount
                .copy(
                    isTemporary = updateCommand.isTemporary,
                    closureDate = updateCommand.closureDate,
                    initialDeposit = updateCommand.initialDeposit,
                    holder = updateCommand.holder?.toDomain() ?: currentAccount.holder,
                )

            accountPort.updateAccount(accountUuid, accountToUpdate)
        } ?: throw throw NotFoundException("Account not found - id: $accountUuid")

    @Transactional
    override fun deleteAccount(accountUuid: UUID) {
        accountPort.deleteAccount(accountUuid)
    }
}

private fun CreateAccountCommand.toDomain(): Account {
    return Account(
        id = null,
        type = type,
        openingDate = openingDate,
        isTemporary = isTemporary,
        closureDate = closureDate,
        initialDeposit = initialDeposit,
        holder = holder.toDomain(),
    )
}

private fun UpsertPersonCommand.toDomain() = Person(
    firstName = firstName,
    lastName = lastName,
    dateOfBirth = dateOfBirth,
    email = email,
)
