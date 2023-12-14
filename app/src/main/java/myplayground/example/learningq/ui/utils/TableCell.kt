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
fun RowScope.TableCellText(
    modifier: Modifier = Modifier,
    weight: Float = 1F,
    textColor: Color = Color.Unspecified,
    text: String = "",
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.onSurface)
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun RowScope.TableCell(
    modifier: Modifier = Modifier,
    weight: Float = 1F,
    content: @Composable (modifier: Modifier) -> Unit = {},
) {
    content(
        modifier
            .border(1.dp, MaterialTheme.colorScheme.onSurface)
            .weight(weight)
            .padding(8.dp),
    )
}