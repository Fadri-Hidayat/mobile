package myplayground.example.learningq.ui.screens.student.quiz_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import myplayground.example.learningq.model.QuizQuestion

data class StudentQuizDetailData(
    val questionList: List<QuizQuestion>? = null,
    val isLoadingQuestionList: Boolean = false,

    val currentQuestionIndex: Int = 0, // 0 = first question, 1 = second question , etc...
    val listEssayAnswer: SnapshotStateList<String> = mutableStateListOf(),
    val listSelectedMultipleChoice: SnapshotStateList<Int> = mutableStateListOf(), // 1 = A, 2 = B, 3 = C, etc...
)

sealed class StudentQuizDetailEvent {
    data class FetchQuestions(val quizId: String) : StudentQuizDetailEvent()
    data class EssayAnswerChanged(val essayAnswer: String) : StudentQuizDetailEvent()
    data class SelectedMultipleChoiceChanged(val choice: Int) : StudentQuizDetailEvent()

    object Init : StudentQuizDetailEvent()
    object PrevQuestion : StudentQuizDetailEvent()
    object NextQuestion : StudentQuizDetailEvent()
    object Submit : StudentQuizDetailEvent()

    sealed class ValidationEvent {
        class None() : ValidationEvent()
        class Success() : ValidationEvent()
        class Failure(val code: Int = 0, val msg: String) : ValidationEvent()
    }
}
