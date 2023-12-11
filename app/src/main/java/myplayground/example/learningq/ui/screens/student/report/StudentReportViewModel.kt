package myplayground.example.learningq.ui.screens.student.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import myplayground.example.learningq.model.Class
import myplayground.example.learningq.repository.Repository

class StudentReportViewModel(
    private val repository: Repository,
) : ViewModel() {
    private val _classState: MutableStateFlow<PagingData<Class>> =
        MutableStateFlow(PagingData.empty())
    val classState: StateFlow<PagingData<Class>> = _classState

    init {
        onEvent(StudentReportEvent.Init)
    }

    fun onEvent(event: StudentReportEvent) {
        viewModelScope.launch {
            when (event) {
                is StudentReportEvent.Init -> {
                    repository.fetchStudentClassPaging().distinctUntilChanged().cachedIn(viewModelScope)
                        .collect {
                            _classState.value = it
                        }
                }
            }
        }
    }

}