package myplayground.example.learningq.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.model.Role
import myplayground.example.learningq.model.User
import myplayground.example.learningq.repository.Repository


class GlobalManager(
) {
    private val _appbarTitle = MutableStateFlow("")

    val appbarTitle: StateFlow<String> = _appbarTitle

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun setAppbarTitle(title: String) {
        coroutineScope.launch {
            _appbarTitle.emit(title)
        }
    }

    companion object {
        @Volatile
        private var instance: GlobalManager? = null

        fun getInstance(): GlobalManager {
            return instance ?: synchronized(this) {
                instance ?: GlobalManager().also {
                    instance = it
                }
            }
        }
    }
}