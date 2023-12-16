package myplayground.example.learningq.ui.screens.student.feedback

data class StudentFeedbackInputData(
    val feedbackAnswer: String = "",
)

sealed class StudentFeedbackEvent {
    object Init : StudentFeedbackEvent()
    object Submit: StudentFeedbackEvent()

    data class FeedbackAnswerChanged(val feedback: String): StudentFeedbackEvent()
}