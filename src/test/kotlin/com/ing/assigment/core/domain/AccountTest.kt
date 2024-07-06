package com.ing.assigment.core.domain

import com.ing.assigment.testsupport.dsl.account
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class AccountTest {

    @Test
    fun `should create Account when all constraints are met`() {
        val account = account { }

        assertNotNull(account)
    }

    @Test
    fun `should throw exception when closure date is null for temporary account`() {
        val exception = assertThrows<IllegalArgumentException> {
            account {
                isTemporary = true
                closureDate = null
            }
        }
        assertEquals("Closure date is mandatory for temporary accounts", exception.message)
    }

    @Test
    fun `should throw exception when closure date is earlier than opening date plus 2 months`() {
        val openingDate = LocalDateTime.now()
        val closureDate = openingDate.plusMonths(1)

        val exception = assertThrows<IllegalArgumentException> {
            account {
                this.openingDate = openingDate
                this.closureDate = closureDate
            }
        }
        assertEquals("Closure date cannot be earlier than the opening date + 2 months", exception.message)
    }

    @Test
    fun `should create Account when closure date is null for non-temporary account`() {
        val account = account {
            isTemporary = false
            closureDate = null
        }
        assertNotNull(account)
    }

    @Test
    fun `should throw exception when initial deposit is negative`() {
        val exception = assertThrows<IllegalArgumentException> {
            account {
                initialDeposit = -100.0
            }
        }
        assertEquals("Initial deposit cannot be negative", exception.message)
    }
}
