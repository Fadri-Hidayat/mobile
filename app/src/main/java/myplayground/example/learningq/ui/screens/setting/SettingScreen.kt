package myplayground.example.learningq.ui.screens.setting

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.learningq.ThemeViewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.ui.components.CustomSwitch
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    ),
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()

    SettingContent(
        modifier = modifier,
        isDarkModeChecked = isDarkMode,
        onDarkModeChanged = themeViewModel::setDarkMode,
    )
}

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    isDarkModeChecked: Boolean = false,
    onDarkModeChanged: (Boolean) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp, 10.dp),
    ) {
        CustomSwitch(
            checked = isDarkModeChecked,
            onCheckedChange = onDarkModeChanged,
            text = "Dark Mode"
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SettingContentPreview() {
    LearningQTheme {
        SettingContent()
    }
}

