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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

        Text(text = "Expanded ${isMenuExpanded.value.toString()}")

        // Create a DropdownMenu with items loaded from the API
        Box(
            modifier = Modifier.padding(16.dp)
        ) {

            ExposedDropdownMenuBox(
                expanded = isMenuExpanded.value,
                onExpandedChange = {
                    isMenuExpanded.value = !isMenuExpanded.value
                },
            ) {
                TextField(
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

        // Display the selected item
        inputData.selectedClass?.let { selected ->
            Text(text = "Selected Item: ${selected.name}")
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

