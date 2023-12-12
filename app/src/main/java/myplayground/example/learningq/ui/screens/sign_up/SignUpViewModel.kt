package myplayground.example.learningq.ui.screens.sign_up

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.repository.UserRegisterInput
import myplayground.example.learningq.ui.utils.StringValidationRule
import myplayground.example.learningq.ui.utils.validate
import myplayground.example.learningq.utils.allNull
import myplayground.example.learningq.utils.allTrue
import retrofit2.HttpException

class SignUpViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState = mutableStateOf(SignUpInputData())
    val uiState: State<SignUpInputData> = _uiState

    private val validationEvent = MutableSharedFlow<SignUpUIEvent.ValidationEvent>()

    fun onEvent(event: SignUpUIEvent) {
        when (event) {
            is SignUpUIEvent.NameChanged -> {
                _uiState.value = _uiState.value.copy(name = event.name)
            }

            is SignUpUIEvent.UsernameChanged -> {
                _uiState.value = _uiState.value.copy(username = event.username)
            }

            is SignUpUIEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password)
            }

            is SignUpUIEvent.ConfirmPasswordChanged -> {
                _uiState.value = _uiState.value.copy(confirmPassword = event.confirmPassword)
            }

            is SignUpUIEvent.Submit -> {
                validateAndSubmit()
            }
        }
    }

    private fun validateAndSubmit() {
        val nameResultError = _uiState.value.name.validate(
            StringValidationRule.Required("Required field")
        ).toErrorMessage()
        val usernameResultError = _uiState.value.username.validate(
            StringValidationRule.Required("Required field")
        ).toErrorMessage()
        val passwordResultError = _uiState.value.password.validate(
            StringValidationRule.Required("Required field")
        ).toErrorMessage()
        val confirmPasswordResultError = _uiState.value.confirmPassword.validate(
            StringValidationRule.Required("Required field"),
            StringValidationRule.SameValueAs(_uiState.value.password, "Password doesn't match")
        ).toErrorMessage()

        _uiState.value = _uiState.value.copy(nameError = nameResultError)
        _uiState.value = _uiState.value.copy(usernameError = usernameResultError)
        _uiState.value = _uiState.value.copy(passwordError = passwordResultError)
        _uiState.value = _uiState.value.copy(confirmPasswordError = confirmPasswordResultError)

        val hasError = !listOf(
            nameResultError,
            usernameResultError,
            passwordResultError,
            confirmPasswordResultError,
        ).allNull()

        viewModelScope.launch {
            if (!hasError) {
                try {
                    repository.userRegister(
                        UserRegisterInput(
                            name = _uiState.value.name,
                            username = _uiState.value.username,
                            password = _uiState.value.password,
                        )
                    ).collect { token ->
                        validationEvent.emit(
                            SignUpUIEvent.ValidationEvent.Success(
                                token
                            )
                        )
                    }
                } catch (e: HttpException) {
                    _uiState.value =
                        _uiState.value.copy(formError = e.response()?.errorBody()?.string())
                }
            } else {
                validationEvent.emit(
                    SignUpUIEvent.ValidationEvent.Failure(
                        0,
                        "Validation Failed"
                    )
                )
            }
        }
    }

}