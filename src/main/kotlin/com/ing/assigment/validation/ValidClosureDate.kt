package com.ing.assigment.validation

import com.ing.assigment.core.command.CreateAccountCommand
import com.ing.assigment.core.command.UpdateAccountCommand
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import java.time.LocalDateTime
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ClosureDateValidator::class])
annotation class ValidClosureDate(
    val message: String = "Invalid closure date",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class ClosureDateValidator : ConstraintValidator<ValidClosureDate, Any> {
    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        return when (value) {
            is CreateAccountCommand -> validateCreateAccountCommand(value)
            is UpdateAccountCommand -> validateUpdateAccountCommand(value)
            else -> true
        }
    }

    private fun validateCreateAccountCommand(command: CreateAccountCommand): Boolean {
        return if (command.isTemporary) {
            val openingPlusTwoMonths = command.openingDate.plusMonths(2)
            command.closureDate != null && command.closureDate.isAfter(openingPlusTwoMonths)
        } else {
            true
        }
    }

    private fun validateUpdateAccountCommand(command: UpdateAccountCommand): Boolean {
        return if (command.isTemporary) {
            val currentPlusOneMonth = LocalDateTime.now().plusMonths(1)
            command.closureDate != null && command.closureDate.isAfter(currentPlusOneMonth)
        } else {
            true
        }
    }
}
