package myplayground.example.learningq.ui.screens.student.quiz_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import myplayground.example.learningq.repository.Repository

class StudentQuizDetailViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState = mutableStateOf(StudentQuizDetailInputData())
    private val _isLoading = mutableStateOf(false)

    val uiState: State<StudentQuizDetailInputData> = _uiState
    val isLoading: State<Boolean> = _isLoading

    val validationEvent = MutableSharedFlow<StudentQuizDetailEvent.ValidationEvent>()

    fun onEvent(event: StudentQuizDetailEvent) {
        when (event) {
            is StudentQuizDetailEvent.EssayAnswerChanged -> {
                _uiState.value = _uiState.value.copy(essayAnswer = event.essayAnswer)
            }

            is StudentQuizDetailEvent.SelectedMultipleChoiceChanged -> {
                if (_uiState.value.selectedMultipleChoice == event.choice) {
                    // reset value
                    _uiState.value = _uiState.value.copy(selectedMultipleChoice = 0)
                } else {
                    _uiState.value = _uiState.value.copy(selectedMultipleChoice = event.choice)
                }
            }

            is StudentQuizDetailEvent.Submit -> {

            }
        }
    }
}