package myplayground.example.learningq.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Header",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 60.sp,
        )
    }
}

@Composable
fun DrawerBody(
    modifier: Modifier = Modifier,
    items: List<MenuItem> = listOf(),
    onItemClick: (menuItems: MenuItem) -> Unit = {}
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        onItemClick(item)
                    }
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String?,
    val icon: ImageVector,
)


