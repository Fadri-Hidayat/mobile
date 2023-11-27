package myplayground.example.learningq

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import myplayground.example.learningq.ui.layout.Appbar
import myplayground.example.learningq.ui.navigation.Screen
import myplayground.example.learningq.ui.screens.home.HomeScreen
import myplayground.example.learningq.ui.screens.landing.LandingScreen
import myplayground.example.learningq.ui.screens.sign_in.SignInScreen

@Composable
fun LearningQApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val hasNavPreviousBackStack by remember(navBackStackEntry) {
        derivedStateOf {
            navController.previousBackStackEntry != null
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (!(currentRoute == Screen.SignIn.route ||
                        currentRoute == Screen.Landing.route
                        )
            ) {
                Appbar(
                    navController = navController,
                    displayBackButton = hasNavPreviousBackStack,
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Landing.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Screen.Landing.route) {
                LandingScreen(
                    navController = navController
                )
            }
            composable(Screen.SignIn.route) {
                SignInScreen(
                    navController = navController,
                )
            }
            composable(Screen.Home.route) {
                HomeScreen()
            }
        }
    }
}


