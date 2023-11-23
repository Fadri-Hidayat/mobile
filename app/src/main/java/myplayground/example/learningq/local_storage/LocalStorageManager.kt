package myplayground.example.learningq.local_storage

import kotlinx.coroutines.flow.Flow

interface LocalStorageManager {
    suspend fun saveDarkThemeSettings(isDarkTheme: Boolean)

    fun getDarkThemeSettingsAsync(): Flow<Boolean>

    suspend fun getDarkThemeSettings(): Boolean
}