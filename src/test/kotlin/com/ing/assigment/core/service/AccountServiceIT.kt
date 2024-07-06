package com.ing.assigment.core.service

import com.ing.assigment.clients.persistence.AccountRepository
import com.ing.assigment.core.command.CreateAccountCommand
import com.ing.assigment.core.command.UpdateAccountCommand
import com.ing.assigment.core.command.UpsertPersonCommand
import com.ing.assigment.core.domain.AccountType
import com.ing.assigment.testsupport.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.LocalDateTime

@IntegrationTest
class AccountServiceIT {

    @Autowired
    private lateinit var accountService: IAccountService

    @Autowired
    private lateinit var accountRepository: AccountRepository

    private fun createDefaultCreateAccountCommand(): CreateAccountCommand {
        return CreateAccountCommand(
            type = AccountType.CURRENT,
            openingDate = LocalDateTime.now(),
            isTemporary = false,
            closureDate = null,
            initialDeposit = 100.0,
            holder = UpsertPersonCommand(
                firstName = "John",
                lastName = "Wick",
                dateOfBirth = LocalDate.now().minusYears(44),
                email = "john.w@hitman.com",
            ),
        )
    }

    @Test
    fun `given valid input, should create account successfully`() {
        // Given
        val createAccountCommand = createDefaultCreateAccountCommand()

        // When
        val createdAccount = accountService.createAccount(createAccountCommand)

        // Then
        assertNotNull(createdAccount.id)
        assertEquals(createAccountCommand.type, createdAccount.type)
        assertEquals(createAccountCommand.openingDate, createdAccount.openingDate)
        assertEquals(createAccountCommand.isTemporary, createdAccount.isTemporary)
        assertEquals(createAccountCommand.closureDate, createdAccount.closureDate)
        assertEquals(createAccountCommand.initialDeposit, createdAccount.initialDeposit)

        assertEquals(createAccountCommand.holder.firstName, createdAccount.holder.firstName)
        assertEquals(createAccountCommand.holder.lastName, createdAccount.holder.lastName)
        assertEquals(createAccountCommand.holder.dateOfBirth, createdAccount.holder.dateOfBirth)
        assertEquals(createAccountCommand.holder.email, createdAccount.holder.email)
    }

    @Test
    fun `given invalid input, should throw error`() {
        // Given
        val createAccountCommand = createDefaultCreateAccountCommand().copy(initialDeposit = -100.0)

        // When, Then
        val exception = assertThrows<IllegalArgumentException> {
            accountService.createAccount(createAccountCommand)
        }
        assertTrue(exception.message!!.contains("Initial deposit cannot be negative"))
    }

    @Test
    fun `given existing account id, when requested, should return the corresponding account`() {
        // Given
        val createAccountCommand = createDefaultCreateAccountCommand()
        val createdAccount = accountService.createAccount(createAccountCommand)

        // When
        val retrievedAccount = accountService.getAccount(createdAccount.id!!)

        // Then
        assertEquals(createdAccount, retrievedAccount)
    }

    @Test
    fun `given existing account id, when updated, should update the corresponding account`() {
        // Given
        val createAccountCommand = createDefaultCreateAccountCommand()
        val createdAccount = accountService.createAccount(createAccountCommand)

        val updateAccountCommand = UpdateAccountCommand(
            isTemporary = true,
            closureDate = LocalDateTime.now().plusMonths(4),
            initialDeposit = 401.0,
            holder = UpsertPersonCommand(
                firstName = "Black",
                lastName = "Widow",
                dateOfBirth = LocalDate.now().minusYears(34),
                email = "black.widow@superhero.com",
            ),
        )

        // When
        val updatedAccount = accountService.updateAccount(createdAccount.id!!, updateAccountCommand)

        // Then
        assertEquals(createdAccount.id, updatedAccount.id)
        assertEquals(createdAccount.type, updatedAccount.type)
        assertEquals(createdAccount.openingDate, updatedAccount.openingDate)
        assertEquals(updateAccountCommand.isTemporary, updatedAccount.isTemporary)
        assertEquals(updateAccountCommand.closureDate, updatedAccount.closureDate)
        assertEquals(updateAccountCommand.initialDeposit, updatedAccount.initialDeposit)

        assertEquals(updateAccountCommand.holder?.firstName, updatedAccount.holder.firstName)
        assertEquals(updateAccountCommand.holder?.lastName, updatedAccount.holder.lastName)
        assertEquals(updateAccountCommand.holder?.dateOfBirth, updatedAccount.holder.dateOfBirth)
        assertEquals(updateAccountCommand.holder?.email, updatedAccount.holder.email)
    }

    @Test
    fun `given existing account id, should be able to delete it`() {
        // Given
        val createAccountCommand = createDefaultCreateAccountCommand()
        val createdAccount = accountService.createAccount(createAccountCommand)

        // When
        accountService.deleteAccount(createdAccount.id!!)

        // Then
        assertNull(accountRepository.findById(createdAccount.id!!).orElse(null))
    }
}
