package myplayground.example.learningq.ui.screens.teacher.feedback_detail

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.model.Feedback
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.utils.AuthManager
import myplayground.example.learningq.utils.Classifier
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TeacherFeedbackDetailViewModel(
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
) : ViewModel() {
    private val _uiState = mutableStateOf(TeacherFeedbackDetailData())

    val uiState: State<TeacherFeedbackDetailData> = _uiState

    init {
        onEvent(TeacherFeedbackDetailEvent.Init)
    }

    fun onEvent(event: TeacherFeedbackDetailEvent) {
        viewModelScope.launch {
            when (event) {
                is TeacherFeedbackDetailEvent.Init -> {
                }

                is TeacherFeedbackDetailEvent.FetchFeedback -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingFetchFeedback = true,
                    )

                    val feedback = repository.fetchFeedbackById(
                        event.feedbackId, Injection.provideApiService(localStorageManager)
                    )

                    _uiState.value = _uiState.value.copy(
                        feedback = feedback,
                        isLoadingFetchFeedback = false,
                    )
                }
            }
        }
    }

}