package myplayground.example.learningq.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import myplayground.example.learningq.ui.theme.LearningQTheme

@Composable
fun Appbar(
    navController: NavHostController,
    displayBackButton: Boolean = false,
    title: String = "",
    onNavigationIconClick: () -> Unit = {},
    trailing: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(start = 12.dp, end = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (displayBackButton) {
                Icon(imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Arrow Back",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            navController.navigateUp()
                        })
            } else {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            onNavigationIconClick()
                        }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleSmall,
            )


            Spacer(modifier = Modifier.weight(1F))

            Box(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                trailing()
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppbarPreview() {
    LearningQTheme {
        Appbar(
            title = "Appbar",
            navController = rememberNavController(),
        )
    }
}