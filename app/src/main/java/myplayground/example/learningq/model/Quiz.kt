package myplayground.example.learningq.model

sealed class QuizQuestionType {
    object MultipleChoice : QuizQuestionType()
    object Essay : QuizQuestionType()
}

data class Quiz(
    val id: String = "",
    val courseId: String = "",
    val name: String = "",
    val totalQuestion: Int = 0,
)

data class QuizQuestion (
    val id: String = "",
    val quizId: String = "",
    val title: String = "",
    val quizType: QuizQuestionType,
    val multipleChoiceList: List<String>?,
    val multipleChoiceAnswerIndex: Int? = null, // 1 = A, 2 = B, etc...
)
