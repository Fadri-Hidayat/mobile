package myplayground.example.learningq.ui.utils

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val errorMessage: String) : ValidationResult()

    fun toErrorMessage(): String? {
        return when (this) {
            is Valid -> {
                null
            }

            is Invalid -> {
                this.errorMessage
            }
        }
    }
}

sealed class StringValidationRule {
    data class Required(val errorMessage: String) : StringValidationRule()

    data class Email(val errorMessage: String) : StringValidationRule()
    data class SameValueAs(val value2: String, val errorMessage: String) : StringValidationRule()
}

sealed class IntValidationRule {
    data class GreaterThanOrEqual(val minValue: Int, val errorMessage: String) : IntValidationRule()
    data class LessThanOrEqual(val maxValue: Int, val errorMessage: String) : IntValidationRule()
}


fun String.validate(vararg rules: StringValidationRule): ValidationResult {
    for (rule in rules) {
        when (rule) {
            is StringValidationRule.Required -> {
                if (this.isEmpty()) {
                    return ValidationResult.Invalid(
                        rule.errorMessage
                    )
                }
            }

            is StringValidationRule.Email -> {
                val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
                if (!emailRegex.matches(this)) {
                    return ValidationResult.Invalid(
                        rule.errorMessage
                    )
                }
            }

            is StringValidationRule.SameValueAs -> {
                if (this != rule.value2) {
                    return ValidationResult.Invalid(rule.errorMessage)
                }
            }
        }
    }
    return ValidationResult.Valid
}

fun Int.validate(vararg rules: IntValidationRule): ValidationResult {
    for (rule in rules) {
        return when (rule) {
            is IntValidationRule.GreaterThanOrEqual -> if (this >= rule.minValue) ValidationResult.Valid else ValidationResult.Invalid(
                rule.errorMessage
            )

            is IntValidationRule.LessThanOrEqual -> if (this <= rule.maxValue) ValidationResult.Valid else ValidationResult.Invalid(
                rule.errorMessage
            )
        }
    }
    return ValidationResult.Valid
}