package com.ing.assigment.validation

import io.mockk.mockk
import jakarta.validation.ConstraintValidatorContext
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class OpeningDateValidatorTest {

    private var validator: OpeningDateValidator = OpeningDateValidator()
    private val mockConstraintValidatorContext = mockk<ConstraintValidatorContext>()

    @Test
    fun `valid opening date - now`() {
        val now = LocalDateTime.now()
        assertTrue(validator.isValid(now, mockConstraintValidatorContext))
    }

    @Test
    fun `valid opening date - between now and 30 days ago`() {
        val validDate = LocalDateTime.now().minusDays(15)
        assertTrue(validator.isValid(validDate, mockConstraintValidatorContext))
    }

    @Test
    fun `invalid opening date - more than 30 days ago`() {
        val invalidDate = LocalDateTime.now().minusDays(31)
        assertFalse(validator.isValid(invalidDate, mockConstraintValidatorContext))
    }

    @Test
    fun `invalid opening date - in the future`() {
        val futureDate = LocalDateTime.now().plusDays(1)
        assertFalse(validator.isValid(futureDate, mockConstraintValidatorContext))
    }
}
