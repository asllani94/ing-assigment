package com.ing.assigment.core.service

import com.ing.assigment.core.command.CreateAccountCommand
import com.ing.assigment.core.command.UpdateAccountCommand
import com.ing.assigment.core.domain.Account
import java.util.UUID

interface IAccountService {

    fun createAccount(createAccountCommand: CreateAccountCommand): Account

    fun getAccount(accountUuid: UUID): Account?

    fun updateAccount(accountUuid: UUID, updateCommand: UpdateAccountCommand): Account

    fun deleteAccount(accountUuid: UUID)
}
