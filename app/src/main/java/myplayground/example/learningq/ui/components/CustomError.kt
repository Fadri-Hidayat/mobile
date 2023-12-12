package myplayground.example.learningq.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import myplayground.example.learningq.ui.theme.LearningQTheme
import kotlin.math.min

@Composable
fun CustomError(
    modifier: Modifier = Modifier,
    error: String = "",
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(
                MaterialTheme.colorScheme.error.copy(
                    red = min(
                        1f,
                        MaterialTheme.colorScheme.error.red * 4.0f
                    ),
                    green = min(
                        1f,
                        MaterialTheme.colorScheme.error.green * 4.0f
                    ),
                    blue = min(
                        1f,
                        MaterialTheme.colorScheme.error.blue * 4.0f
                    )
                )
            )
            .border(
                (1.5).dp,
                MaterialTheme.colorScheme.error,
                MaterialTheme.shapes.small,
            )
            .padding(16.dp, 8.dp),
    ) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error.copy(
                red = min(
                    1f,
                    MaterialTheme.colorScheme.error.red * 0.6f,
                ),
                blue = min(
                    1f,
                    MaterialTheme.colorScheme.error.blue * 0.6f,
                ),
                green = min(
                    1f,
                    MaterialTheme.colorScheme.error.green * 0.6f,
                )
            ),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CustomErrorPreview() {
    LearningQTheme {
        CustomError(
            error = "Error"
        )
    }
}