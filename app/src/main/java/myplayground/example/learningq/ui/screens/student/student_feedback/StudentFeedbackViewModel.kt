package myplayground.example.learningq.ui.screens.student.student_feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import myplayground.example.learningq.model.Class
import myplayground.example.learningq.repository.Repository

class StudentFeedbackViewModel(
    private val repository: Repository,
) : ViewModel() {
    private val _classState: MutableStateFlow<PagingData<Class>> =
        MutableStateFlow(PagingData.empty())
    val classState: StateFlow<PagingData<Class>> = _classState

    init {
        onEvent(StudentFeedbackEvent.Init)
    }

    fun onEvent(event: StudentFeedbackEvent) {
        viewModelScope.launch {
            when (event) {
                is StudentFeedbackEvent.Init -> {
                }

                is StudentFeedbackEvent.FeedbackAnswerChanged -> {

                }
            }
        }
    }

}