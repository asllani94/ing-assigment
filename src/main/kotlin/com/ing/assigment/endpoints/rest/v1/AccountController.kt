package com.ing.assigment.endpoints.rest.v1

import com.ing.assigment.core.command.CreateAccountCommand
import com.ing.assigment.core.command.UpdateAccountCommand
import com.ing.assigment.core.command.UpsertPersonCommand
import com.ing.assigment.core.domain.Account
import com.ing.assigment.core.domain.Person
import com.ing.assigment.core.service.IAccountService
import com.ing.assigment.dto.account.AccountDto
import com.ing.assigment.dto.account.CreateAccountDto
import com.ing.assigment.dto.account.UpdateAccountDto
import com.ing.assigment.dto.person.PersonDto
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
    fun createAccount(@Valid @RequestBody createAccountDto: CreateAccountDto): ResponseEntity<AccountDto> =
        accountService
            .createAccount(createAccountDto.toCommand())
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
        @Valid @RequestBody updateAccountDto: UpdateAccountDto,
    ): ResponseEntity<AccountDto> =
        accountService
            .updateAccount(accountUuid, updateAccountDto.toCommand())
            .toDto().let {
                ResponseEntity.ok(it)
            }

    @DeleteMapping("/{accountUuid}")
    fun deleteAccount(@PathVariable accountUuid: UUID): ResponseEntity<Any> =
        accountService.deleteAccount(accountUuid).let {
            ResponseEntity.noContent().build()
        }
}

private fun CreateAccountDto.toCommand(): CreateAccountCommand {
    return CreateAccountCommand(
        openingDate = openingDate,
        type = type,
        isTemporary = isTemporary,
        closureDate = closureDate,
        initialDeposit = initialDeposit,
        holder = holder.toCommand(),
    )
}

private fun UpdateAccountDto.toCommand(): UpdateAccountCommand {
    return UpdateAccountCommand(
        isTemporary = isTemporary,
        closureDate = closureDate,
        initialDeposit = initialDeposit,
        holder = holder?.toCommand(),
    )
}

private fun PersonDto.toCommand(): UpsertPersonCommand {
    return UpsertPersonCommand(
        firstName = firstName,
        lastName = lastName,
        dateOfBirth = dateOfBirth,
        email = email,
    )
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
