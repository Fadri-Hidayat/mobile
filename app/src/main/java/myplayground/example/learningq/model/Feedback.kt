package myplayground.example.learningq.model

data class Feedback(
    val id: String = "",
    val quizId: String = "",
    val studentUserId: String = "",
    val teacherUserId: String = "",
    val content: String = "",

    val isGoodResponse: Boolean? = null,

    val quiz: Quiz? = null,
    val studentUser: User? = null,
    val teacherUser: User? = null,
)