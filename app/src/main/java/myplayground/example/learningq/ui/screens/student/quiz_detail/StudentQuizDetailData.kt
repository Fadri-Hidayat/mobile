package myplayground.example.learningq.ui.screens.student.quiz_detail

data class StudentQuizDetailInputData(
    val essayAnswer: String = "",
)

sealed class StudentQuizDetailEvent {
    data class EssayAnswerChanged(val essayAnswer: String) : StudentQuizDetailEvent()

    object Submit : StudentQuizDetailEvent()

    sealed class ValidationEvent {
        class None() : ValidationEvent()
        class Success() : ValidationEvent()
        class Failure(val code: Int = 0, val msg: String) : ValidationEvent()
    }
}
