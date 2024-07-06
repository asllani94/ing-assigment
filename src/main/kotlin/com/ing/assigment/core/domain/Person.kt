package com.ing.assigment.core.domain

import java.time.LocalDate

data class Person(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String,
) {
    init {
        require(firstName.length in 3..35) {
            "First name must be between 3 and 35 characters"
        }
        require(lastName.length in 3..35) {
            "Last name must be between 3 and 35 characters"
        }

        require(email.matches(Regex(VALID_EMAIL_ADDRESS_REGEX))) {
            "Email must be a well-formed email address"
        }

        require(dateOfBirth.isBefore(LocalDate.now().minusYears(18))) {
            "Person must be at least 18 years old"
        }
    }
    companion object {
        const val VALID_EMAIL_ADDRESS_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"
    }
}
