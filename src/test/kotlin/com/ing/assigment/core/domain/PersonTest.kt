package com.ing.assigment.core.domain

import com.ing.assigment.testsupport.dsl.person
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class PersonTest {

    @Test
    fun `should create Person when all constraints are met`() {
        val person = person { }
        assertNotNull(person)
    }

    @Test
    fun `should throw exception when firstName is too short`() {
        val exception = assertThrows<IllegalArgumentException> {
            person {
                firstName = "Jo"
            }
        }
        assertEquals("First name must be between 3 and 35 characters", exception.message)
    }

    @Test
    fun `should throw exception when firstName is too long`() {
        val exception = assertThrows<IllegalArgumentException> {
            person {
                firstName = "J".repeat(36)
            }
        }
        assertEquals("First name must be between 3 and 35 characters", exception.message)
    }

    @Test
    fun `should throw exception when lastName is too short`() {
        val exception = assertThrows<IllegalArgumentException> {
            person {
                lastName = "Do"
            }
        }
        assertEquals("Last name must be between 3 and 35 characters", exception.message)
    }

    @Test
    fun `should throw exception when lastName is too long`() {
        val exception = assertThrows<IllegalArgumentException> {
            person {
                lastName = "D".repeat(36)
            }
        }
        assertEquals("Last name must be between 3 and 35 characters", exception.message)
    }

    @Test
    fun `should throw exception when dateOfBirth indicates person is younger than 18`() {
        val exception = assertThrows<IllegalArgumentException> {
            person {
                dateOfBirth = LocalDate.now().minusYears(17)
            }
        }
        assertEquals("Person must be at least 18 years old", exception.message)
    }

    @Test
    fun `should throw exception when email is invalid`() {
        val exception = assertThrows<IllegalArgumentException> {
            person {
                email = "invalid-email"
            }
        }
        assertEquals("Email must be a well-formed email address", exception.message)
    }

    @Test
    fun `should create Person when email is valid`() {
        val person = person {
            email = "john.doe@mail.com"
        }
        assertNotNull(person)
    }
}
