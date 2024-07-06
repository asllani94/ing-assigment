package com.ing.assigment.core.service

import com.ing.assigment.core.command.CreateAccountCommand
import com.ing.assigment.core.command.UpdateAccountCommand
import com.ing.assigment.core.command.UpsertPersonCommand
import com.ing.assigment.core.domain.Person
import com.ing.assigment.core.port.AccountPort
import com.ing.assigment.errorhandling.NotFoundException
import com.ing.assigment.testsupport.dsl.account
import com.ing.assigment.testsupport.dsl.person
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class AccountServiceTest {

    private val accountPort: AccountPort = mockk()
    private val accountService = AccountService(accountPort)

    @Test
    fun `should create account successfully`() {
        val account = account { }

        val createAccountCommand = CreateAccountCommand(
            type = account.type,
            openingDate = account.openingDate,
            isTemporary = account.isTemporary,
            closureDate = account.closureDate,
            initialDeposit = account.initialDeposit,
            holder = account.holder.toUpsertCommand(),
        )

        every { accountPort.createAccount(any()) } returns account

        val createdAccount = accountService.createAccount(createAccountCommand)

        assertNotNull(createdAccount)
        verify(exactly = 1) { accountPort.createAccount(any()) }
    }

    @Test
    fun `should return account by ID`() {
        val account = account { }
        val accountId = account.id!!

        every { accountPort.findAccount(accountId) } returns account

        val foundAccount = accountService.getAccount(accountId)

        assertNotNull(foundAccount)
        assertEquals(account.id, foundAccount?.id)
        verify(exactly = 1) { accountPort.findAccount(accountId) }
    }

    @Test
    fun `should throw NotFoundException when updating non-existent account`() {
        val accountId = UUID.randomUUID()
        val updateCommand = UpdateAccountCommand(
            isTemporary = false,
            closureDate = null,
            initialDeposit = 2000.0,
            holder = null,
        )

        every { accountPort.findAccount(accountId) } returns null

        val exception = assertThrows<NotFoundException> {
            accountService.updateAccount(accountId, updateCommand)
        }

        assertEquals("Account not found - id: $accountId", exception.message)
        verify(exactly = 1) { accountPort.findAccount(accountId) }
    }

    @Test
    fun `should update account successfully`() {
        val currentAccount = account { }
        val accountId = currentAccount.id!!

        val holderUpdate = person {
            firstName = "Jane"
            lastName = "Joplin"
            dateOfBirth = LocalDate.now().minusYears(40)
            email = "jane.joplin@myemail.net"
        }

        val updateCommand = UpdateAccountCommand(
            isTemporary = true,
            closureDate = LocalDateTime.now().plusMonths(3),
            initialDeposit = 2000.0,
            holder = holderUpdate.toUpsertCommand(),
        )

        val updatedAccount = currentAccount.copy(
            isTemporary = updateCommand.isTemporary,
            closureDate = updateCommand.closureDate,
            initialDeposit = updateCommand.initialDeposit,
            holder = holderUpdate,
        )

        every { accountPort.findAccount(accountId) } returns currentAccount
        every { accountPort.updateAccount(accountId, updatedAccount) } returns updatedAccount

        val result = accountService.updateAccount(accountId, updateCommand)

        assertNotNull(result)
        assertEquals(updatedAccount.isTemporary, result.isTemporary)
        assertEquals(updatedAccount.closureDate, result.closureDate)
        assertEquals(updatedAccount.initialDeposit, result.initialDeposit)

        assertEquals(updatedAccount.holder.firstName, result.holder.firstName)
        assertEquals(updatedAccount.holder.firstName, result.holder.firstName)
        assertEquals(updatedAccount.holder.dateOfBirth, result.holder.dateOfBirth)
        assertEquals(updatedAccount.holder.email, result.holder.email)

        verify(exactly = 1) { accountPort.findAccount(accountId) }
        verify(exactly = 1) { accountPort.updateAccount(accountId, updatedAccount) }
    }

    @Test
    fun `should delete account successfully`() {
        val accountId = UUID.randomUUID()

        every { accountPort.deleteAccount(accountId) } just runs

        accountService.deleteAccount(accountId)

        verify(exactly = 1) { accountPort.deleteAccount(accountId) }
    }
}

fun Person.toUpsertCommand() = UpsertPersonCommand(
    firstName = firstName,
    lastName = lastName,
    dateOfBirth = dateOfBirth,
    email = email,
)
