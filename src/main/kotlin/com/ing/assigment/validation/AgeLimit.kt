package com.ing.assigment.validation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import java.time.LocalDate
import java.time.Period
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [AgeLimitValidator::class])
annotation class AgeLimit(
    val message: String = "User must be at least 18 years old",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class AgeLimitValidator : ConstraintValidator<AgeLimit, LocalDate> {

    override fun isValid(dateOfBirth: LocalDate, context: ConstraintValidatorContext): Boolean {
        return Period.between(dateOfBirth, LocalDate.now()).years >= 18
    }
}
