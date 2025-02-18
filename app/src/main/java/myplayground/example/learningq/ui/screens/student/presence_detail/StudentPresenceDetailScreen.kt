package myplayground.example.learningq.ui.screens.student.presence_detail

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.model.Course
import myplayground.example.learningq.ui.components.CustomButton
import myplayground.example.learningq.ui.components.shimmerBrush
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory
import myplayground.example.learningq.utils.CustomDayOfWeek
import myplayground.example.learningq.utils.TimeInSeconds

@Composable
fun StudentPresenceDetailScreen(
    modifier: Modifier = Modifier,
    vm: StudentPresenceDetailViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    ),
    classId: String = "",
) {
    val coursesPagingItem = vm.courseState.collectAsLazyPagingItems()
    val inputData by vm.inputData

    LaunchedEffect(classId) {
        vm.onEvent(StudentPresenceDetailEvent.FetchPresence(classId))
    }

    StudentPresenceDetailContent(
        modifier = modifier,
        coursesPagingItem = coursesPagingItem,
        inputData = inputData,
        vm::onEvent,
    )
}

@Composable
fun StudentPresenceDetailContent(
    modifier: Modifier = Modifier,
    coursesPagingItem: LazyPagingItems<Course>? = null,
    inputData: StudentPresenceDetailData = StudentPresenceDetailData(),
    onEvent: (StudentPresenceDetailEvent) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        coursesPagingItem?.let { classesPagingItem ->

            items(classesPagingItem.itemCount) { index ->
                val currentCourse = classesPagingItem[index]!!

                StudentPresenceDetailCard(
                    studentCourse = currentCourse,
                    inputData = inputData,
                    onPresenceClick = {
                        onEvent(StudentPresenceDetailEvent.CoursePresenceSubmit(currentCourse))
                    },
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            classesPagingItem.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        items(10) {
                            StudentPresenceDetailCardSkeletonView()
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = classesPagingItem.loadState.refresh as LoadState.Error
                        item {
                            Text(error.toString())
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center),
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = classesPagingItem.loadState.append as LoadState.Error
                        item {
                            Text(error.toString())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentPresenceDetailCard(
    studentCourse: Course,
    inputData: StudentPresenceDetailData = StudentPresenceDetailData(),
    onPresenceClick: () -> Unit = {},
) {
    val isLoading = inputData.processedCourseId == studentCourse.id && inputData.isLoading

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = studentCourse.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = studentCourse.dayOfWeek.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = "${studentCourse.startTimeInMinutes.toString()} - ${studentCourse.endTimeInMinutes.toString()}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = MaterialTheme.shapes.small,
                isLoading = isLoading,
                enabled = !isLoading,
                onClick = {
                    onPresenceClick()
                },
            ) {
                Text(
                    text = "Presence",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}

@Composable
fun StudentPresenceDetailCardSkeletonView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(shimmerBrush()),
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun StudentPresenceDetailContentPreview() {
    LearningQTheme {
        StudentPresenceDetailContent()
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun StudentPresenceDetailCardPreview() {
    LearningQTheme {
        StudentPresenceDetailCard(
            Course(
                id = "1",
                name = "Class A",
                dayOfWeek = CustomDayOfWeek.MONDAY,
                startTimeInMinutes = TimeInSeconds(3600),
                endTimeInMinutes = TimeInSeconds(7200)
            )
        )
    }
}

