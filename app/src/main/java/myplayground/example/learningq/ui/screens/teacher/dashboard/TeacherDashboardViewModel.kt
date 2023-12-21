package myplayground.example.learningq.ui.screens.teacher.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.model.Course
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.utils.AuthManager

class TeacherDashboardViewModel(
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
    private val authManager: AuthManager,
) :
    ViewModel() {
    private val _courseState: MutableStateFlow<PagingData<Course>> =
        MutableStateFlow(PagingData.empty())
    private val user = authManager.user.value

    val courseState: StateFlow<PagingData<Course>> = _courseState

    init {
        onEvent(TeacherDashboardEvent.Init)
    }

    fun onEvent(event: TeacherDashboardEvent) {
        viewModelScope.launch {
            when (event) {
                is TeacherDashboardEvent.Init -> {
                    repository.fetchTeacherCoursePaging(
                        user!!.id,
                        Injection.provideApiService(localStorageManager = localStorageManager),
                    )
                        .distinctUntilChanged()
                        .cachedIn(viewModelScope)
                        .collect {
                            _courseState.value = it
                        }
                }
            }
        }
    }

}