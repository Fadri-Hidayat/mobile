package myplayground.example.learningq.ui.screens.landing

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import myplayground.example.learningq.ui.navigation.Screen
import myplayground.example.learningq.ui.theme.LearningQTheme

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    LandingContent(
        modifier = modifier,
        navController = navController,
    )
}

@Composable
fun LandingContent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier.weight(1F),
                onClick = {
                    navController.navigate(Screen.SignIn.route)
                }
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Button(
                modifier = Modifier.weight(1F),
                onClick = {
                    navController.navigate(Screen.SignUp.route)
                },
            ) {
                Text("Register")
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LandingContentPreview() {
    LearningQTheme {
        LandingContent(
            modifier = Modifier,
        )
    }
}
