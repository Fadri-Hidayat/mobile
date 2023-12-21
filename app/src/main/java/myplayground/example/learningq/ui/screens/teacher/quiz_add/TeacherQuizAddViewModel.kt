package myplayground.example.learningq.ui.screens.teacher.quiz_add

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
import myplayground.example.learningq.utils.AuthManager

class TeacherQuizAddViewModel(
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
    private val authManager: AuthManager,
) :
    ViewModel() {
    private val _uiState = mutableStateOf(TeacherQuizAddData())
    private val user = authManager.user.value

    val uiState: State<TeacherQuizAddData> = _uiState


    init {
        onEvent(TeacherQuizAddEvent.Init)
    }

    fun onEvent(event: TeacherQuizAddEvent) {
        viewModelScope.launch {
            when (event) {
                is TeacherQuizAddEvent.Init -> {
                    val classes = repository.fetchTeacherClassByTeacherUserId(
                        user!!.id,
                        Injection.provideApiService(localStorageManager = localStorageManager)
                    )

                    _uiState.value = _uiState.value.copy(
                        classList = classes.toMutableStateList(),
                    )
                }


                is TeacherQuizAddEvent.ClassSelected -> {
                    _uiState.value = _uiState.value.copy(selectedClass = event.selectedClass)
                }

                is TeacherQuizAddEvent.TitleChanged -> {
                    _uiState.value = _uiState.value.copy(title = event.title)
                }

                is TeacherQuizAddEvent.TotalQuestionChanged -> {
                    _uiState.value = _uiState.value.copy(
                        totalQuestion = event.totalQuestion,
                    )
                }
            }
        }
    }

}