package com.ing.assigment.validation

import io.mockk.mockk
import jakarta.validation.ConstraintValidatorContext
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AgeLimitValidatorTest {

    private var validator: AgeLimitValidator = AgeLimitValidator()

    private val mockConstraintValidatorContext = mockk<ConstraintValidatorContext>()

    @Test
    fun `valid age - more than 18 years old`() {
        val dateOfBirth = LocalDate.now().minusYears(19)
        assertTrue(validator.isValid(dateOfBirth, mockConstraintValidatorContext))
    }

    @Test
    fun `invalid age - less than 18 years old`() {
        val dateOfBirth = LocalDate.now().minusYears(17)
        assertFalse(validator.isValid(dateOfBirth, mockConstraintValidatorContext))
    }

    @Test
    fun `edge case - exactly 18 years old`() {
        val dateOfBirth = LocalDate.now().minusYears(18)
        assertTrue(validator.isValid(dateOfBirth, mockConstraintValidatorContext))
    }

    @Test
    fun `edge case - one day less than 18 years old`() {
        val dateOfBirth = LocalDate.now().minusYears(18).plusDays(1)
        assertFalse(validator.isValid(dateOfBirth, mockConstraintValidatorContext))
    }
}
