package myplayground.example.learningq.ui.screens.student.student_feedback

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.ui.components.CustomOutlinedTextField
import myplayground.example.learningq.ui.screens.teacher.quiz_add.TeacherQuizAddEvent
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun StudentFeedbackScreen(
    modifier: Modifier = Modifier, vm: StudentFeedbackViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    )
) {


    StudentFeedbackContent(
        modifier = modifier,
    )
}

@Composable
fun StudentFeedbackContent(
    modifier: Modifier = Modifier,
    inputData: StudentFeedbackInputData = StudentFeedbackInputData(),
    event: (StudentFeedbackEvent) -> Unit = {},
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Feedback Form",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = "Give feedback on the learning activities that have been carried out, if there are suggesstions or input please write them down!",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelMedium,
        )


        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputData.title,
            onValueChange = {
                onEvent(TeacherQuizAddEvent.DescriptionChanged(it))
            },
            minLines = 3,
            maxLines = 5,
//            enabled = !isLoading,
            label = {
                Text(
                    "Description",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
//            isError = inputData.hasUsernameError,
//            supportingText = {
//                if (inputData.hasUsernameError) {
//                    Text(
//                        "Temporary Input Error",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.error,
//                    )
//                }
//            },
        )
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun StudentFeedbackContentPreview() {
    LearningQTheme {
        StudentFeedbackContent()
    }
}
