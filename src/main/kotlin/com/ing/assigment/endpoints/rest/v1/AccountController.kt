package com.ing.assigment.endpoints.rest.v1

import com.ing.assigment.core.command.CreateAccountCommand
import com.ing.assigment.core.command.UpdateAccountCommand
import com.ing.assigment.core.domain.Account
import com.ing.assigment.core.domain.Person
import com.ing.assigment.core.service.IAccountService
import com.ing.assigment.dto.AccountDto
import com.ing.assigment.dto.PersonDto
import com.ing.assigment.errorhandling.NotFoundException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountService: IAccountService) {

    @PostMapping
    fun createAccount(@Valid @RequestBody createAccountCommand: CreateAccountCommand): ResponseEntity<AccountDto> =
        accountService
            .createAccount(createAccountCommand)
            .toDto()
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @GetMapping("/{accountUuid}")
    fun getAccount(@PathVariable accountUuid: UUID): ResponseEntity<AccountDto> =
        accountService.getAccount(accountUuid)?.let {
            ResponseEntity.ok(it.toDto())
        } ?: throw NotFoundException("Account not found - id: $accountUuid")

    @PutMapping("/{accountUuid}")
    fun updateAccount(
        @PathVariable accountUuid: UUID,
        @Valid @RequestBody updateAccountCommand: UpdateAccountCommand,
    ): ResponseEntity<AccountDto> =
        accountService
            .updateAccount(accountUuid, updateAccountCommand)
            .toDto().let {
                ResponseEntity.ok(it)
            }

    @DeleteMapping("/{accountUuid}")
    fun deleteAccount(@PathVariable accountUuid: UUID): ResponseEntity<Any> =
        accountService.deleteAccount(accountUuid).let {
            ResponseEntity.noContent().build()
        }
}

private fun Account.toDto(): AccountDto {
    return AccountDto(
        id = id!!,
        type = type,
        openingDate = this.openingDate,
        isTemporary = this.isTemporary,
        closureDate = this.closureDate,
        initialDeposit = this.initialDeposit,
        holder = this.holder.toDto(),
    )
}

private fun Person.toDto(): PersonDto {
    return PersonDto(
        firstName = firstName,
        lastName = lastName,
        dateOfBirth = dateOfBirth,
        email = email,
    )
}
