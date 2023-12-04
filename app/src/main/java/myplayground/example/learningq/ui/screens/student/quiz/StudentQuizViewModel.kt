package myplayground.example.learningq.ui.screens.student.quiz

import androidx.lifecycle.ViewModel
import myplayground.example.learningq.repository.Repository

class StudentQuizViewModel(private val repository: Repository) : ViewModel() {
    //    private val _uiState = mutableStateOf(SignUpInputData())
    //    val uiState: State<SignUpInputData> = _uiState
    //
    //    private val validationEvent = MutableSharedFlow<SignUpUIEvent.ValidationEvent>()
    //
    //    fun onEvent(event: SignUpUIEvent) {
    //        when (event) {
    //            is SignUpUIEvent.NameChanged -> {
    //                _uiState.value = _uiState.value.copy(name = event.name)
    //            }
    //
    //            is SignUpUIEvent.UsernameChanged -> {
    //                _uiState.value = _uiState.value.copy(username = event.username)
    //            }
    //
    //            is SignUpUIEvent.PasswordChanged -> {
    //                _uiState.value = _uiState.value.copy(password = event.password)
    //            }
    //
    //            is SignUpUIEvent.ConfirmPasswordChanged -> {
    //                _uiState.value = _uiState.value.copy(confirmPassword = event.confirmPassword)
    //            }
    //
    //            is SignUpUIEvent.Submit -> {
    //                validateAndSubmit()
    //            }
    //        }
    //    }
    //
    //    private fun validateAndSubmit() {
    //        val nameResultError = _uiState.value.name.isEmpty()
    //        val usernameResultError = _uiState.value.username.isEmpty()
    //        val passwordResultError = _uiState.value.password.isEmpty()
    //        val confirmPasswordResultError = _uiState.value.confirmPassword.isEmpty()
    //
    //        _uiState.value = _uiState.value.copy(hasNameError = nameResultError)
    //        _uiState.value = _uiState.value.copy(hasUsernameError = usernameResultError)
    //        _uiState.value = _uiState.value.copy(hasPasswordError = passwordResultError)
    //        _uiState.value = _uiState.value.copy(hasConfirmPasswordError = confirmPasswordResultError)
    //
    //        val hasError = listOf(
    //            nameResultError,
    //            usernameResultError,
    //            passwordResultError,
    //            confirmPasswordResultError,
    //        ).allTrue()
    //
    //        viewModelScope.launch {
    //            if (!hasError) {
    //                repository.userRegister(
    //                    UserRegisterInput(
    //                        name = _uiState.value.name,
    //                        username = _uiState.value.username,
    //                        password = _uiState.value.password,
    //                    )
    //                ).collect { token ->
    //                    validationEvent.emit(
    //                        SignUpUIEvent.ValidationEvent.Success(
    //                            token
    //                        )
    //                    )
    //                }
    //
    //            } else {
    //                validationEvent.emit(
    //                    SignUpUIEvent.ValidationEvent.Failure(
    //                        0,
    //                        "Validation Failed"
    //                    )
    //                )
    //            }
    //        }
    //    }
}