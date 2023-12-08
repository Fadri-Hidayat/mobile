package myplayground.example.learningq.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FabWrapper(
    modifier: Modifier = Modifier,
    fab: @Composable (fabModifier: Modifier) -> Unit = {},
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        content()

        fab(
            Modifier
                .align(Alignment.BottomEnd)
                .offset(
                    x = (-28).dp,
                    y = (-30).dp
                ),
        )
    }
}