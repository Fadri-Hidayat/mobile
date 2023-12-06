package myplayground.example.learningq.ui.screens.student.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.repository.Repository

class StudentQuizViewModel(private val repository: Repository) : ViewModel() {
    private val _quizState: MutableStateFlow<PagingData<Quiz>> =
        MutableStateFlow(PagingData.empty())
    val quizState: StateFlow<PagingData<Quiz>> = _quizState

    init {
        onEvent(StudentQuizEvent.Init)
    }

    fun onEvent(event: StudentQuizEvent) {
        viewModelScope.launch {
            when (event) {
                is StudentQuizEvent.Init -> {
                    repository.fetchQuizPaging()
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