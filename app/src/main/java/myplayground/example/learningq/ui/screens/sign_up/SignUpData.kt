package myplayground.example.learningq.ui.screens.sign_up

import myplayground.example.learningq.model.Token

data class SignUpInputData(
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val hasNameError: Boolean = false,
    val hasUsernameError: Boolean = false,
    val hasPasswordError: Boolean = false,
    val hasConfirmPasswordError: Boolean = false,
)

sealed class SignUpUIEvent {
    data class NameChanged(val name: String) : SignUpUIEvent()
    data class UsernameChanged(val username: String) : SignUpUIEvent()
    data class PasswordChanged(val password: String) : SignUpUIEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpUIEvent()

    object Submit : SignUpUIEvent()

    sealed class ValidationEvent {
        class Success(val token: Token?) : ValidationEvent()
        class Failure(val code: Int = 0, val msg: String) : ValidationEvent()
    }
}