package myplayground.example.learningq.ui.screens.student.quiz_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.repository.Repository

class StudentQuizDetailViewModel(
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
) : ViewModel() {
    private val _uiState = mutableStateOf(StudentQuizDetailData())
    private val _isLoading = mutableStateOf(false)

    val uiState: State<StudentQuizDetailData> = _uiState
    val isLoading: State<Boolean> = _isLoading


    fun onEvent(event: StudentQuizDetailEvent) {
        when (event) {
            is StudentQuizDetailEvent.Init -> {
            }

            is StudentQuizDetailEvent.FetchQuestions -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(
                        isLoadingQuestionList = true,
                    )

                    val questions = repository.fetchStudentQuizQuestionByQuizId(
                        event.quizId,
                        Injection.provideApiService(localStorageManager),
                    )

                    _uiState.value = _uiState.value.copy(
                        questionList = questions,
                        isLoadingQuestionList = false,
                        listEssayAnswer = MutableList(questions.size) { "" }.toMutableStateList(),
                        listSelectedMultipleChoice = MutableList(questions.size) { 0 }.toMutableStateList(),
                    )
                }
            }

            is StudentQuizDetailEvent.EssayAnswerChanged -> {
                val updatedListEssayAnswer = _uiState.value.listEssayAnswer
                updatedListEssayAnswer[_uiState.value.currentQuestionIndex] = event.essayAnswer
                _uiState.value = _uiState.value.copy(
                    listEssayAnswer = updatedListEssayAnswer,
                )
            }

            is StudentQuizDetailEvent.SelectedMultipleChoiceChanged -> {
                val updatedListMultipleChoice = _uiState.value.listSelectedMultipleChoice
                updatedListMultipleChoice[_uiState.value.currentQuestionIndex] = event.choice
                _uiState.value = _uiState.value.copy(
                    listSelectedMultipleChoice = updatedListMultipleChoice,
                )
            }

            is StudentQuizDetailEvent.Submit -> {

            }

            is StudentQuizDetailEvent.NextQuestion -> {
                _uiState.value = _uiState.value.copy(
                    currentQuestionIndex = _uiState.value.currentQuestionIndex + 1,
                )
            }

            is StudentQuizDetailEvent.PrevQuestion -> {
                _uiState.value = _uiState.value.copy(
                    currentQuestionIndex = _uiState.value.currentQuestionIndex - 1,
                )
            }

        }
    }
}