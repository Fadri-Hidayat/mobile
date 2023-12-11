package myplayground.example.learningq.ui.screens.student.quiz_detail

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.ui.components.CustomOutlinedTextField
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun StudentQuizDetailScreen(
    modifier: Modifier = Modifier,
    vm: StudentQuizDetailViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        ),
    ),
    quizId: String,
) {
    val inputData by vm.uiState

    StudentQuizDetailContent(
        modifier = modifier,
        inputData,
        vm::onEvent,
        isEssay = quizId.toInt() % 2 == 0,
    )
}

@Composable
fun StudentQuizDetailContent(
    modifier: Modifier = Modifier,
    inputData: StudentQuizDetailInputData = StudentQuizDetailInputData(),
    onEvent: (StudentQuizDetailEvent) -> Unit = {},
    isEssay: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "Question 1 of 10",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ac interdum nisl. Donec eleifend felis vitae orci malesuada elementum. Nulla eget commodo eros",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge,
        )

        Spacer(modifier = Modifier.height(24.dp))


        if (isEssay) {
            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = inputData.essayAnswer,
                onValueChange = {
                    onEvent(StudentQuizDetailEvent.EssayAnswerChanged(it))
                },
                minLines = 4,
                maxLines = 6,
            )
        } else {
            OutlinedButton(
                onClick = {},
            ) {
                Text(
                    text = "A. Answer A",
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {},
            ) {
                Text(
                    text = "B. Answer B",
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {},
            ) {
                Text(
                    text = "C. Answer C",
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {},
            ) {
                Text(
                    text = "D. Answer D",
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.weight(1F))

        Row {
            Button(onClick = {}) {
                Text(
                    text = "Previous",
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            Spacer(
                modifier = Modifier
                    .weight(1F)
            )

            Button(onClick = {}) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun StudentQuizDetailContentMultipleChoicePreview() {
    LearningQTheme {
        StudentQuizDetailContent()
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun StudentQuizDetailContentEssayPreview() {
    LearningQTheme {
        StudentQuizDetailContent(
            isEssay = true,
        )
    }
}

