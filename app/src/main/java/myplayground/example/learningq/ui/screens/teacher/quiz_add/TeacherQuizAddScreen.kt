package myplayground.example.learningq.ui.screens.teacher.quiz_add

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun TeacherQuizAddScreen(
    modifier: Modifier = Modifier,
    vm: TeacherQuizAddViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    )
) {

    TeacherQuizAddContent(
        modifier = modifier,
    )
}

@Composable
fun TeacherQuizAddContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "Create a new quiz: ",
            style = MaterialTheme.typography.titleSmall,
        )
        Spacer(modifier = Modifier.height(28.dp))
        Row {
            Button(
                modifier = Modifier.weight(1F),
                onClick = {},
            ) {
                Text("Essay")
            }

            Spacer(modifier = Modifier.width(40.dp))

            Button(
                modifier = Modifier.weight(1F),
                onClick = {},
            ) {
                Text("Pilgan")
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TeacherQuizAddContentPreview() {
    LearningQTheme {
        TeacherQuizAddContent()
    }
}

