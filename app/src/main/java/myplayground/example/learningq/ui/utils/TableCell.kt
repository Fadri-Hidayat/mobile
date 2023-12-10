package myplayground.example.learningq.ui.utils;

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableCell(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = Color.Unspecified,
    weight: Float = 1F,
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.primary)
            .weight(weight)
            .padding(8.dp)
    )
}