package myplayground.example.learningq.ui.screens.teacher.feedback_detail

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.model.Feedback
import myplayground.example.learningq.ui.screens.student.quiz.StudentQuizCardSkeletonView
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun TeacherFeedbackDetailScreen(
    modifier: Modifier = Modifier,
    vm: TeacherFeedbackDetailViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    ),
    feedbackId: String = "",
) {
    val inputData by vm.uiState

    LaunchedEffect(null) {
        vm.onEvent(TeacherFeedbackDetailEvent.FetchFeedback(feedbackId))
    }

    TeacherFeedbackDetailContent(
        modifier = modifier,
        inputData = inputData,
    )
}

@Composable
fun TeacherFeedbackDetailContent(
    modifier: Modifier = Modifier,
    inputData: TeacherFeedbackDetailData = TeacherFeedbackDetailData(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = inputData.feedback?.quiz?.course?.name ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = inputData.feedback?.studentUser?.name ?: "",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                .padding(12.dp)
                .clip(MaterialTheme.shapes.medium),
        ) {
            if (!inputData.isLoadingFetchFeedback) {
                Text(
                    text = inputData.feedback?.content ?: "",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TeacherFeedbackDetailContentPreview() {
    LearningQTheme {
        TeacherFeedbackDetailContent()
    }
}


