package myplayground.example.learningq.ui.screens.admin.user

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
import myplayground.example.learningq.model.User
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.ui.screens.student.dashboard.StudentDashboardEvent

class AdminUserViewModel(
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
) :
    ViewModel() {
    private val _userState: MutableStateFlow<PagingData<User>> =
        MutableStateFlow(PagingData.empty())
    val userState: StateFlow<PagingData<User>> = _userState

    init {
        onEvent(StudentDashboardEvent.Init)
    }

    fun onEvent(event: StudentDashboardEvent) {
        viewModelScope.launch {
            when (event) {
                is StudentDashboardEvent.Init -> {
                    repository.fetchUserPaging(
                        Injection.provideFakeApiService(localStorageManager)
                    )
                        .distinctUntilChanged()
                        .cachedIn(viewModelScope)
                        .collect {
                            _userState.value = it
                        }
                }
            }
        }
    }
}