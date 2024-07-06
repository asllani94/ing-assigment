package com.ing.assigment.clients.persistence

import com.ing.assigment.testsupport.dsl.account
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import java.util.Optional
import java.util.UUID
import kotlin.test.Test

class AccountAdapterTest {

    private val accountRepositoryMock = mockk<AccountRepository>()

    private val sut = AccountAdapter(accountRepository = accountRepositoryMock)

    @Test
    fun `createAccount should save and return account`() {
        // Given
        val account = account { }
        val accountEntity = account.toEntity()

        every { accountRepositoryMock.save(any()) } returns accountEntity

        // When
        val result = sut.createAccount(account)

        // Then
        verify { accountRepositoryMock.save(accountEntity) }
        assertEquals(account, result)
    }

    @Test
    fun `findAccount should return account when found`() {
        val accountId = UUID.randomUUID()
        val accountEntity = account { }.toEntity()

        every { accountRepositoryMock.findById(accountId) } returns Optional.of(accountEntity)

        val result = sut.findAccount(accountId)

        verify { accountRepositoryMock.findById(accountId) }
        assertNotNull(result)
        assertEquals(accountEntity.toDomain(), result)
    }

    @Test
    fun `findAccount should return null when not found`() {
        val accountId = UUID.randomUUID()

        every { accountRepositoryMock.findById(accountId) } returns Optional.empty()

        val result = sut.findAccount(accountId)

        verify { accountRepositoryMock.findById(accountId) }
        assertNull(result)
    }

    @Test
    fun `updateAccount should save and return updated account`() {
        val accountId = UUID.randomUUID()
        val account = account { }
        val accountEntity = account.toEntity()

        every { accountRepositoryMock.save(any()) } returns accountEntity

        val result = sut.updateAccount(accountId, account)

        verify { accountRepositoryMock.save(accountEntity) }
        assertEquals(account, result)
    }

    @Test
    fun `deleteAccount should delete account by id`() {
        val accountId = UUID.randomUUID()

        every { accountRepositoryMock.deleteById(accountId) } just Runs

        sut.deleteAccount(accountId)

        verify { accountRepositoryMock.deleteById(accountId) }
    }
}
