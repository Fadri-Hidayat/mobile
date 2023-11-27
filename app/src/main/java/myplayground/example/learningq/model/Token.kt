package myplayground.example.learningq.model

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val auth_token: String,
)
