package myplayground.example.learningq.ui.screens.admin.user_add

data class AdminUserAddInputData(
    val feedbackAnswer: String = "",

    val name: String = "",
    val email: String = "",
    val password: String = "",
)

sealed class AdminUserAddEvent {
    object Init : AdminUserAddEvent()
    object Submit: AdminUserAddEvent()

    data class FeedbackAnswerChanged(val feedback: String): AdminUserAddEvent()
    data class NameChanged(val name: String): AdminUserAddEvent()
    data class EmailChanged(val email: String): AdminUserAddEvent()
    data class PasswordChanged(val password: String): AdminUserAddEvent()
}