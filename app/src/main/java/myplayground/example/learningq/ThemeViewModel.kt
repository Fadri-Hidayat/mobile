package myplayground.example.learningq

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import myplayground.example.learningq.local_storage.LocalStorageManager

open class ThemeViewModel private constructor(
    private val localStorageManager: LocalStorageManager
) : ViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    open val isDarkMode: StateFlow<Boolean> get() = _isDarkMode

    open fun setDarkMode(darkMode: Boolean) {
        _isDarkMode.value = darkMode

        viewModelScope.launch {
            localStorageManager.saveDarkThemeSettings(isDarkTheme = darkMode)
        }
    }

    init {
        runBlocking {
            _isDarkMode.value = localStorageManager.getDarkThemeSettings()
        }
    }

    companion object {
        @Volatile
        private var instance: ThemeViewModel? = null

        fun getInstance(localStorageManager: LocalStorageManager): ThemeViewModel {
            return instance ?: synchronized(this) {
                val vm = ThemeViewModel(localStorageManager)
                instance = vm
                return vm
            }
        }
    }
}