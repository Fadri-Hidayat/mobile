package myplayground.example.learningq.ui.screens.teacher.quiz

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

class TeacherQuizViewModel(
    private val repository: Repository,
) :
    ViewModel() {
    private val _quizState: MutableStateFlow<PagingData<Quiz>> =
        MutableStateFlow(PagingData.empty())
    val quizState: StateFlow<PagingData<Quiz>> = _quizState

    init {
        onEvent(TeacherQuizEvent.Init)
    }

    fun onEvent(event: TeacherQuizEvent) {
        viewModelScope.launch {
            when (event) {
                is TeacherQuizEvent.Init -> {
                    repository.fetchTeacherQuizPaging()
                        .distinctUntilChanged()
                        .cachedIn(viewModelScope)
                        .collect {
                            _quizState.value = it
                        }
                }
            }
        }
    }

}