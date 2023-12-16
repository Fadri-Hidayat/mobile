package myplayground.example.learningq.ui.screens.student.feedback

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import myplayground.example.learningq.repository.Repository
import retrofit2.HttpException

class StudentFeedbackViewModel(
    private val repository: Repository,
) : ViewModel() {
    private val _uiState = mutableStateOf(StudentFeedbackInputData())
    private val _isLoading = mutableStateOf(false)

    val uiState: State<StudentFeedbackInputData> = _uiState
    val isLoading: State<Boolean> = _isLoading

    init {
        onEvent(StudentFeedbackEvent.Init)
    }

    fun onEvent(event: StudentFeedbackEvent) {
        viewModelScope.launch {
            when (event) {
                is StudentFeedbackEvent.Init -> {
                }

                is StudentFeedbackEvent.FeedbackAnswerChanged -> {
                    _uiState.value = _uiState.value.copy(feedbackAnswer = event.feedback)
                }

                is StudentFeedbackEvent.Submit -> {
                    submitFeedback()
                }
            }
        }
    }

    private suspend fun submitFeedback() {
        _isLoading.value = true

        try {
            delay(1500)
        }catch(e: HttpException) {

        } finally {
            _isLoading.value = false
        }
    }
}