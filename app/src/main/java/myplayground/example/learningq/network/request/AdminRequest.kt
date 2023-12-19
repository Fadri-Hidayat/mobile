package myplayground.example.learningq.network.request

data class AdminCreateUserRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
)