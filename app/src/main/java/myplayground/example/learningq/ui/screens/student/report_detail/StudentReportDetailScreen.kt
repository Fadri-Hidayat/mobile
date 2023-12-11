package myplayground.example.learningq.ui.screens.student.report_detail

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.TableCell
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun StudentReportDetailScreen(
    modifier: Modifier = Modifier, vm: StudentReportDetailViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    )
) {
    StudentReportDetailContent(
        modifier = modifier,
    )
}

@Composable
fun StudentReportDetailContent(
    modifier: Modifier = Modifier,
) {
    val col1Weight = 0.2F
    val col2Weight = 0.5F
    val col3Weight = 0.3F
    val textColor = MaterialTheme.colorScheme.onBackground
    val cellModifier = Modifier.fillMaxHeight()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
        ) {
            TableCell(
                modifier = cellModifier,
                textColor = textColor,
                text = "No",
                weight = col1Weight,
            )
            TableCell(
                modifier = cellModifier,
                textColor = textColor,
                text = "Mata Pelajaran",
                weight = col2Weight,
            )
            TableCell(
                modifier = cellModifier,
                textColor = textColor,
                text = "Nilai",
                weight = col3Weight,
            )
        }

        repeat(12) {
            Row {
                TableCell(
                    modifier = cellModifier,
                    textColor = textColor,
                    text = "1",
                    weight = col1Weight,
                )
                TableCell(
                    modifier = cellModifier,
                    textColor = textColor,
                    text = "Agama",
                    weight = col2Weight,
                )
                TableCell(
                    modifier = cellModifier,
                    textColor = textColor,
                    text = "88",
                    weight = col3Weight,
                )
            }

            Row {
                TableCell(
                    modifier = cellModifier,
                    textColor = textColor,
                    text = "2",
                    weight = col1Weight,
                )
                TableCell(
                    modifier = cellModifier,
                    textColor = textColor,
                    text = "PPKN",
                    weight = col2Weight,
                )
                TableCell(
                    modifier = cellModifier,
                    textColor = textColor,
                    text = "100",
                    weight = col3Weight,
                )
            }

            Row {
                TableCell(
                    modifier = cellModifier,
                    textColor = textColor,
                    text = "3",
                    weight = col1Weight,
                )
                TableCell(
                    modifier = cellModifier,
                    textColor = textColor,
                    text = "Matematika",
                    weight = col2Weight,
                )
                TableCell(
                    modifier = cellModifier,
                    textColor = textColor,
                    text = "90",
                    weight = col3Weight,
                )
            }

        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun StudentReportDetailContentPreview() {
    LearningQTheme {
        StudentReportDetailContent()
    }
}
