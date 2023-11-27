package myplayground.example.learningq.ui.screens.sign_in

import myplayground.example.learningq.model.Token

data class SignInInputData(
    val username: String = "",
    val password: String = "",

    val hasUsernameError: Boolean = false,
    val hasPasswordError: Boolean = false,
)

sealed class SignInUIEvent {
    data class UsernameChanged(val username: String) : SignInUIEvent()
    data class PasswordChanged(val password: String) : SignInUIEvent()

    object Submit : SignInUIEvent()

    sealed class ValidationEvent {
        class Success(val token: Token?) : ValidationEvent()
        class Failure(val code: Int = 0, val msg: String) : ValidationEvent()
    }
}