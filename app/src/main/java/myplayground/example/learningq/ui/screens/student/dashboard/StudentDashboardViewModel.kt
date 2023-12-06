package myplayground.example.learningq.ui.screens.student.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.ui.screens.student.quiz.StudentQuizEvent

class StudentDashboardViewModel(
    private val repository: Repository,
) :
    ViewModel() {
    private val _classState: MutableStateFlow<PagingData<Class>> =
        MutableStateFlow(PagingData.empty())
    val classState: StateFlow<PagingData<Class>> = _classState

    init {
        onEvent(StudentDashboardEvent.Init)
    }

    fun onEvent(event: StudentDashboardEvent) {
        viewModelScope.launch {
            when (event) {
                is StudentDashboardEvent.Init -> {
                    repository.fetchClassPaging()
                        .distinctUntilChanged()
                        .cachedIn(viewModelScope)
                        .collect {
                            _classState.value = it
                        }
                }
            }
        }
    }

}