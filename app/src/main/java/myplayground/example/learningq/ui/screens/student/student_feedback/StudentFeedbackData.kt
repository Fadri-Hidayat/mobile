package myplayground.example.learningq.ui.screens.student.student_feedback

data class StudentFeedbackInputData(
    val feedbackAnswer: String = "",
)

sealed class StudentFeedbackEvent {
    object Init : StudentFeedbackEvent()

    data class FeedbackAnswerChanged(val feedback: String): StudentFeedbackEvent()
}