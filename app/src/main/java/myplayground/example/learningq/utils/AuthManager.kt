package myplayground.example.learningq.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.model.Role
import myplayground.example.learningq.model.User
import myplayground.example.learningq.repository.Repository

class AuthManager(
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
) {
    private val _haveToken = MutableStateFlow(false)
    private val _currentRole: MutableStateFlow<Role?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private var _user = MutableStateFlow<User?>(null)

    val haveToken: StateFlow<Boolean> = _haveToken
    val currentRole: StateFlow<Role?> = _currentRole
    val isLoading: StateFlow<Boolean> = _isLoading
    val user: StateFlow<User?> = _user

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    init {
        coroutineScope.launch {
            localStorageManager.getUserTokenAsync().collect { token ->
                if (token.isNotEmpty()) {
                    _haveToken.value = true
                    _isLoading.value = true

                    _user.value = repository.userMe(token)
                } else {
                    _haveToken.value = false
                    _user.value = null
                }

                _isLoading.value = false
            }
        }

        coroutineScope.launch {
            localStorageManager.getUserRoleAsync().collect { role ->
                _currentRole.value = role
            }

        }
    }

    fun logout() {
        coroutineScope.launch {
            localStorageManager.saveUserToken("")
            localStorageManager.saveUserRole(null)
        }
    }

    fun cancel() {
        coroutineScope.cancel()
    }

    companion object {
        @Volatile
        private var instance: AuthManager? = null

        fun getInstance(
            repository: Repository,
            localStorageManager: LocalStorageManager
        ): AuthManager {
            return instance ?: synchronized(this) {
                instance ?: AuthManager(repository, localStorageManager).also {
                    instance = it
                }
            }
        }
    }
}