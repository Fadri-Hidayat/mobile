package myplayground.example.learningq.local_storage

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import myplayground.example.learningq.model.Role

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "learning_ds")

class DatastoreSettings private constructor(private val dataStore: DataStore<Preferences>) :
    LocalStorageManager {
    override suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_TOKEN] = token
        }
    }

    override fun getUserTokenAsync(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[KEY_USER_TOKEN] ?: ""
        }
    }

    override suspend fun getUserToken(): String {
        return getUserTokenAsync().first()
    }

    override suspend fun saveUserRole(role: Role?) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ROLE] = role?.toString() ?: ""
        }
    }

    override suspend fun getUserRoleAsync(): Flow<Role?> {
        return dataStore.data.map { preferences ->
            if (preferences[KEY_USER_ROLE] == null) null else Role.parseString(preferences[KEY_USER_ROLE]!!)
        }
    }

    override suspend fun getUserRole(): Role? {
        return getUserRoleAsync().first()
    }

    override suspend fun saveDarkThemeSettings(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_DARK_THEME] = isDarkTheme
        }
    }

    override fun getDarkThemeSettingsAsync(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[KEY_DARK_THEME] ?: false
        }
    }

    override suspend fun getDarkThemeSettings(): Boolean {
        return getDarkThemeSettingsAsync().first()
    }


    companion object {
        private val KEY_USER_TOKEN = stringPreferencesKey("user_token")
        private val KEY_USER_ROLE = stringPreferencesKey("user_role")
        private val KEY_DARK_THEME = booleanPreferencesKey("is_dark_theme")

        @Volatile
        private var instance: DatastoreSettings? = null

        fun getInstance(ds: DataStore<Preferences>): DatastoreSettings {
            return instance ?: synchronized(this) {
                val dsSettings = DatastoreSettings(ds)
                instance = dsSettings
                return dsSettings
            }
        }
    }
}