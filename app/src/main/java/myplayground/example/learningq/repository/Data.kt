package myplayground.example.learningq.repository

data class UserLoginInput (
    val username: String = "",
    val password: String = "",
)

data class UserRegisterInput (
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)

