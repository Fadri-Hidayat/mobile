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
    val course: Course? = null,
    val isCompleted: Boolean = false,
)

data class QuizQuestion(
    val id: String = "",
    val quizOrder: Int = 0, // ascending
    var quizId: String = "",
    val title: String = "",
    val quizType: QuizQuestionType,
    val multipleChoiceList: List<String>? = null,
    val multipleChoiceAnswerIndex: Int? = null, // 1 = A, 2 = B, etc...
)
