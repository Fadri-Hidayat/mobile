package myplayground.example.learningq.network.response

import myplayground.example.learningq.model.User

data class LoginResponse(
    val accessToken: String = "",
    val role: String = "",
)

data class MeResponse(
    val user: User,
)
