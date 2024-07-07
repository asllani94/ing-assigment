package com.ing.assigment.validation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import java.time.LocalDateTime
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [OpeningDateValidator::class])
annotation class ValidOpeningDate(
    val message: String = "Opening date must be between now and 30 days in the past",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class OpeningDateValidator : ConstraintValidator<ValidOpeningDate, LocalDateTime> {

    override fun isValid(value: LocalDateTime, context: ConstraintValidatorContext): Boolean {
        val now = LocalDateTime.now()
        val thirtyDaysAgo = now.minusDays(30)

        return !value.isBefore(thirtyDaysAgo) && !value.isAfter(now)
    }
}
