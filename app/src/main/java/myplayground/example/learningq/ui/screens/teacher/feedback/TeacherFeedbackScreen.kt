package myplayground.example.learningq.ui.screens.teacher.feedback

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.model.Course
import myplayground.example.learningq.model.Feedback
import myplayground.example.learningq.ui.screens.student.quiz.StudentQuizCardSkeletonView
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun TeacherFeedbackScreen(
    modifier: Modifier = Modifier,
    vm: TeacherFeedbackViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    ),
    courseId: String = "",
) {
    val inputData by vm.uiState
    val context = LocalContext.current

    LaunchedEffect(null) {
        vm.onEvent(TeacherFeedbackEvent.InitNLP(context))
    }

    LaunchedEffect(courseId) {
        vm.onEvent(
            TeacherFeedbackEvent.TeacherFeedbackFetch(
                context,
                courseId,
            )
        )
    }

    TeacherFeedbackContent(
        modifier = modifier,
        inputData = inputData,
    )
}

@Composable
fun TeacherFeedbackContent(
    modifier: Modifier = Modifier,
    inputData: TeacherFeedbackData = TeacherFeedbackData(),
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        if (inputData.isLoadingFetchFeedback) {
            items(10) {
                StudentQuizCardSkeletonView()
                Spacer(modifier = Modifier.height(12.dp))
            }
        } else {
            items(inputData.listFeedback.toList()) { currentFeedback ->
                TeacherFeedbackCourse(
                    currentFeedback,
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

        }
    }
}

@Composable
fun TeacherFeedbackCourse(
    feedback: Feedback,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
    ) {
        Row(
            modifier = Modifier.padding(8.dp, 8.dp, 12.dp, 8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
                    .widthIn(0.dp, 200.dp),
            ) {
                Text(
                    text = feedback.studentUser?.name ?: "",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = feedback.quiz?.course?.name ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Spacer(modifier = Modifier.weight(1F))

            if (feedback.isGoodResponse != null) {
                TeacherFeedbackGoodBadResponse(feedback.isGoodResponse)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                shape = MaterialTheme.shapes.small,
                onClick = {},
            ) {
                Text(
                    text = "Detail",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}

@Composable
fun TeacherFeedbackGoodBadResponse(isGoodResponse: Boolean) {
    Box(
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(if (isGoodResponse) Color.Green else Color.Red),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            tint = Color.White,
            imageVector = if (isGoodResponse) Icons.Default.ThumbUp else Icons.Default.ThumbDown,
            contentDescription = if (isGoodResponse) "Good Response" else "Bad Response",
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TeacherFeedbackContentPreview() {
    LearningQTheme {
        TeacherFeedbackContent()
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TeacherFeedbackGoodBadResponsePreview() {
    LearningQTheme {
        Row() {
            TeacherFeedbackGoodBadResponse(true)

            Spacer(modifier = Modifier.width(20.dp))

            TeacherFeedbackGoodBadResponse(false)
        }
    }
}

