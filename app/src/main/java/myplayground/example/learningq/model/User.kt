package myplayground.example.learningq.model

data class User(
    val id: String,
    val name: String,
    val role: Role,
    val image_url: String? = "",
)