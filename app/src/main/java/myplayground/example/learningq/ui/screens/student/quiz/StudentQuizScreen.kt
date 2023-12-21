package myplayground.example.learningq.ui.screens.student.quiz

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.ui.components.shimmerBrush
import myplayground.example.learningq.ui.navigation.Screen
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun StudentQuizScreen(
    modifier: Modifier = Modifier, vm: StudentQuizViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        ),
    ),
    navController: NavHostController = rememberNavController()
) {
    val quizPagingItems: LazyPagingItems<Quiz> = vm.quizState.collectAsLazyPagingItems()

    StudentQuizContent(
        modifier = modifier,
        quizPagingItems = quizPagingItems,
    ) { quizId ->
        navController.navigate(Screen.StudentQuizDetail.createRoute(quizId))
    }
}

@Composable
fun StudentQuizContent(
    modifier: Modifier = Modifier,
    quizPagingItems: LazyPagingItems<Quiz>? = null,
    navigateToDetail: (id: String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Quiz",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        item {
            Text(
                text = "Select a quiz to start :",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        quizPagingItems?.let { quizPagingItems ->
            items(quizPagingItems.itemCount) { index ->
                val currentQuiz = quizPagingItems[index]!!

                StudentQuizCard(
                    currentQuiz,
                    navigateToDetail,
                )

                Spacer(modifier = Modifier.height(12.dp))
            }


            quizPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        items(10) {
                            StudentQuizCardSkeletonView()
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = quizPagingItems.loadState.refresh as LoadState.Error
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
                        val error = quizPagingItems.loadState.append as LoadState.Error
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
fun StudentQuizCard(
    quiz: Quiz,
    navigateToDetail: (id: String) -> Unit = {},
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
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .widthIn(0.dp, 240.dp),
                text = quiz.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.weight(1F))

            if (!quiz.isCompleted) {
                Button(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        navigateToDetail(quiz.id)
                    },
                ) {
                    Text(
                        text = "Start",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(
                            Color.Green
                        )
                        .padding(8.dp, 4.dp),
                ) {
                    Text(
                        text = "Completed",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }
}

@Composable
fun StudentQuizCardSkeletonView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(shimmerBrush()),
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun StudentQuizContentPreview() {
    LearningQTheme {
        StudentQuizContent()
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun StudentQuizCardSkeletonViewPreview() {
    LearningQTheme {
        StudentQuizCardSkeletonView()
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun StudentQuizCardPreview() {
    LearningQTheme {
        StudentQuizCard(
            quiz = Quiz(
                id = "1",
                name = "Quiz BAB I",
                isCompleted = false,
            )
        )

        StudentQuizCard(
            quiz = Quiz(
                id = "1",
                name = "Quiz BAB I",
                isCompleted = true,
            )
        )
    }
}
