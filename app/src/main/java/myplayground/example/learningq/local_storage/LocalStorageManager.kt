package myplayground.example.learningq.local_storage

import kotlinx.coroutines.flow.Flow
import myplayground.example.learningq.model.Role

interface LocalStorageManager {
    suspend fun saveUserToken(token: String)

    fun getUserTokenAsync(): Flow<String>

    suspend fun getUserToken(): String

    suspend fun saveUserRole(role: Role?)
    suspend fun getUserRoleAsync(): Flow<Role?>
    suspend fun getUserRole(): Role?

    suspend fun saveDarkThemeSettings(isDarkTheme: Boolean)

    fun getDarkThemeSettingsAsync(): Flow<Boolean>

    suspend fun getDarkThemeSettings(): Boolean
}