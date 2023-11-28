package myplayground.example.learningq

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory
import myplayground.example.learningq.utils.AuthManager

class MainActivity : AppCompatActivity() {
    private val themeViewModel: ThemeViewModel by viewModels {
        ViewModelFactory(
            this.application,
            Injection.provideRepository(context = this),
            DatastoreSettings.getInstance(applicationContext.dataStore),
        )
    }

    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authManager = Injection.provideAuthManager(this)

        setContent {
            val isDarkTheme = themeViewModel.isDarkMode.collectAsState(initial = false)

            LearningQTheme(
                darkTheme = isDarkTheme.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LearningQApp()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        authManager.cancel()
    }
}