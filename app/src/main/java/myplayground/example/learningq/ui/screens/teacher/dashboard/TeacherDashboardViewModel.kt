package myplayground.example.learningq.ui.screens.teacher.dashboard

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

class TeacherDashboardViewModel(
    private val repository: Repository,
) :
    ViewModel() {
    private val _classState: MutableStateFlow<PagingData<Class>> =
        MutableStateFlow(PagingData.empty())
    val classState: StateFlow<PagingData<Class>> = _classState

    init {
        onEvent(TeacherDashboardEvent.Init)
    }

    fun onEvent(event: TeacherDashboardEvent) {
        viewModelScope.launch {
            when (event) {
                is TeacherDashboardEvent.Init -> {
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