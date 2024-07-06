package com.ing.assigment.core.port

import com.ing.assigment.core.domain.Account
import java.util.UUID

interface AccountPort {

    fun createAccount(account: Account): Account

    fun findAccount(accountId: UUID): Account?

    fun updateAccount(accountId: UUID, account: Account): Account

    fun deleteAccount(accountId: UUID)
}
