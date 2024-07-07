package com.ing.assigment.validation

import com.ing.assigment.core.command.CreateAccountCommand
import com.ing.assigment.core.command.UpdateAccountCommand
import com.ing.assigment.core.command.UpsertPersonCommand
import com.ing.assigment.core.domain.AccountType
import io.mockk.mockk
import jakarta.validation.ConstraintValidatorContext
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class ClosureDateValidatorTest {

    private var validator: ClosureDateValidator = ClosureDateValidator()

    private val mockConstraintValidatorContext = mockk<ConstraintValidatorContext>()

    @Test
    fun `GIVEN that create account command has isTemporary=true then closure date is mandatory`() {
        val command = generateCreateAccountCommand(isTemporary = true, closureDate = null)
        assertFalse(validator.isValid(command, mockConstraintValidatorContext))
    }

    @Test
    fun `GIVEN that create account command has isTemporary=true then closure date cannot be earlier than the opening date + 2 months`() {
        val command = generateCreateAccountCommand(isTemporary = true, closureDate = LocalDateTime.now().plusMonths(1))
        assertFalse(validator.isValid(command, mockConstraintValidatorContext))
    }

    @Test
    fun `GIVEN that create account command has isTemporary=false then closure date is not mandatory`() {
        val command = generateCreateAccountCommand(isTemporary = false, closureDate = null)
        assertTrue(validator.isValid(command, mockConstraintValidatorContext))
    }

    @Test
    fun `GIVEN that update account command has isTemporary=true then closure date is mandatory`() {
        val command = generateUpdateAccountCommand(isTemporary = true, closureDate = null)
        assertFalse(validator.isValid(command, mockConstraintValidatorContext))
    }

    @Test
    fun `GIVEN that update account command has isTemporary=true then closure date cannot be earlier than the modification date  + 1 month`() {
        val command = generateUpdateAccountCommand(isTemporary = true, closureDate = LocalDateTime.now().plusDays(1))
        assertFalse(validator.isValid(command, mockConstraintValidatorContext))
    }
}

private fun generateCreateAccountCommand(isTemporary: Boolean = true, closureDate: LocalDateTime?): CreateAccountCommand {
    return CreateAccountCommand(
        type = AccountType.SAVINGS,
        openingDate = LocalDateTime.now(),
        isTemporary = isTemporary,
        closureDate = closureDate,
        initialDeposit = 100.0,
        holder = createValidPersonHolder(),
    )
}

private fun generateUpdateAccountCommand(isTemporary: Boolean = true, closureDate: LocalDateTime?): UpdateAccountCommand {
    return UpdateAccountCommand(
        isTemporary = true,
        closureDate = closureDate,
        initialDeposit = 100.0,
        holder = createValidPersonHolder(),
    )
}

private fun createValidPersonHolder() =
    UpsertPersonCommand("John", "Doe", LocalDate.now().minusYears(30), "john.doe@example.com")
