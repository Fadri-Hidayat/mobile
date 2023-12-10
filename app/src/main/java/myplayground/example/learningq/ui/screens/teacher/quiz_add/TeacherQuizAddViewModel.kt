package myplayground.example.learningq.ui.screens.teacher.quiz_add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.repository.Repository

class TeacherQuizAddViewModel(
    private val repository: Repository,
) :
    ViewModel() {

    private val _uiState = mutableStateOf(TeacherQuizAddInputData())

    val uiState: State<TeacherQuizAddInputData> = _uiState


    init {
        onEvent(TeacherQuizAddEvent.Init)
    }

    fun onEvent(event: TeacherQuizAddEvent) {
        viewModelScope.launch {
            when (event) {
                is TeacherQuizAddEvent.Init -> {

                }

                is TeacherQuizAddEvent.ClassSelected -> {
                    _uiState.value = _uiState.value.copy(selectedClass = event.selectedClass)
                }
            }
        }
    }

}