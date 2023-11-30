package myplayground.example.learningq

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.ui.layout.Appbar
import myplayground.example.learningq.ui.layout.DrawerBody
import myplayground.example.learningq.ui.layout.DrawerHeader
import myplayground.example.learningq.ui.layout.MenuItem
import myplayground.example.learningq.ui.navigation.Screen
import myplayground.example.learningq.ui.screens.home.HomeScreen
import myplayground.example.learningq.ui.screens.landing.LandingScreen
import myplayground.example.learningq.ui.screens.sign_in.SignInScreen
import myplayground.example.learningq.ui.screens.sign_up.SignUpScreen

@Composable
fun LearningQApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val hasNavPreviousBackStack by remember(navBackStackEntry) {
        derivedStateOf {
            navController.previousBackStackEntry != null
        }
    }

    val authManager =
        Injection.provideAuthManager(LocalContext.current.applicationContext as Application)

    val haveToken = authManager.haveToken.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (haveToken.value) {
                ModalDrawerSheet {
                    DrawerHeader()
                    DrawerBody(
                        items = listOf(
                            MenuItem(
                                id = "home",
                                title = "Home",
                                contentDescription = "Home",
                                icon = Icons.Default.Home,
                            ),
                            MenuItem(
                                id = "logout",
                                title = "Logout",
                                contentDescription = "Logout",
                                icon = Icons.Default.Logout,
                            ),
                        ),
                        onItemClick = { menuItem ->
                            when (menuItem.id) {
                                "home" -> {

                                }

                                "logout" -> {
                                    authManager.logout()
                                }
                            }

                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                }
            }
        }) {
        Scaffold(
            modifier = modifier,
            topBar = {
                if (!(currentRoute == Screen.SignIn.route ||
                            currentRoute == Screen.SignUp.route ||
                            currentRoute == Screen.Landing.route
                            )
                ) {
                    Appbar(
                        navController = navController,
                        displayBackButton = hasNavPreviousBackStack,
                        onNavigationIconClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = if (haveToken.value) Screen.Home.route else Screen.Landing.route,
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

                composable(Screen.SignUp.route) {
                    SignUpScreen(
                        navController = navController
                    )
                }

                composable(Screen.Home.route) {
                    HomeScreen()
                }
            }
        }
    }
}


