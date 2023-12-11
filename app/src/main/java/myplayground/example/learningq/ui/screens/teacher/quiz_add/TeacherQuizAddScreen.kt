package myplayground.example.learningq.ui.screens.teacher.quiz_add

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.model.Class
import myplayground.example.learningq.ui.components.CustomOutlinedTextField
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
    val inputData by vm.uiState
    val isMenuExpanded = remember { mutableStateOf(false) }

    TeacherQuizAddContent(
        modifier = modifier,
        inputData = inputData,
        isMenuExpanded,
        vm::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherQuizAddContent(
    modifier: Modifier = Modifier,
    inputData: TeacherQuizAddInputData = TeacherQuizAddInputData(),
    isMenuExpanded: MutableState<Boolean> = mutableStateOf(false),
    onEvent: (TeacherQuizAddEvent) -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "Create a new quiz: ",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
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

        Spacer(modifier = Modifier.height(16.dp))


        Box {

            ExposedDropdownMenuBox(
                expanded = isMenuExpanded.value,
                onExpandedChange = {
                    isMenuExpanded.value = !isMenuExpanded.value
                },
            ) {
                CustomOutlinedTextField(
                    value = "",
                    onValueChange = {
                    },
                    label = { Text("Search") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .clickable {
                        }

                )

                ExposedDropdownMenu(
                    expanded = isMenuExpanded.value,
                    onDismissRequest = {
                        isMenuExpanded.value = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { ->
                    listOf(
                        Class(
                            id = "1",
                            name = "Class A",
                        ),
                        Class(
                            id = "2",
                            name = "Class B",
                        ),
                        Class(
                            id = "3",
                            name = "Class C",
                        ),
                    ).forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                onEvent(TeacherQuizAddEvent.ClassSelected(item))
                                isMenuExpanded.value = false
                            },
                            text = {
                                Text(text = item.name)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputData.title,
            onValueChange = {
                onEvent(TeacherQuizAddEvent.TitleChanged(it))
            },
//            enabled = !isLoading,
            label = {
                Text(
                    "Title",
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

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.weight(1F))

        Button(
            modifier = Modifier
                .padding(24.dp, 0.dp)
                .align(Alignment.End),
            onClick = {},
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.titleSmall,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
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

