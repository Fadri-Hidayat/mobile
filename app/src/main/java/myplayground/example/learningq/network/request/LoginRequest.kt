package myplayground.example.learningq.network.request

import java.io.Serializable

data class LoginRequest (
    val email: String,
    val password: String,
): Serializable