package myplayground.example.learningq

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.model.Role
import myplayground.example.learningq.ui.layout.Appbar
import myplayground.example.learningq.ui.layout.DrawerBodyStudent
import myplayground.example.learningq.ui.layout.DrawerHeader
import myplayground.example.learningq.ui.navigation.Screen
import myplayground.example.learningq.ui.screens.home.HomeScreen
import myplayground.example.learningq.ui.screens.landing.LandingScreen
import myplayground.example.learningq.ui.screens.setting.SettingScreen
import myplayground.example.learningq.ui.screens.sign_in.SignInScreen
import myplayground.example.learningq.ui.screens.sign_up.SignUpScreen
import myplayground.example.learningq.ui.screens.student.dashboard.StudentDashboardScreen
import myplayground.example.learningq.ui.screens.student.quiz.StudentQuizScreen
import myplayground.example.learningq.ui.screens.student.quiz_detail.StudentQuizDetailScreen

@Composable
fun LearningQApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val scope = rememberCoroutineScope()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val hasNavPreviousBackStack = navController.previousBackStackEntry != null

    val authManager =
        Injection.provideAuthManager(LocalContext.current.applicationContext as Application)

    val haveToken = authManager.haveToken.collectAsState()
    val isLoading = authManager.isLoading.collectAsState()
    val user = authManager.user.collectAsState()

    val startDestination = if (haveToken.value) {
        when (user.value?.role) {
            is Role.Student -> {
                Screen.StudentDashboard.route
            }

            else -> ""
        }
    } else {
        Screen.Landing.route
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (haveToken.value) {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        DrawerHeader(
                            user = user.value,
                        )
                        if (user.value != null) {
                            when (user.value!!.role) {
                                Role.Student -> {
                                    DrawerBodyStudent(
                                        modifier = Modifier.weight(1F),
                                        navController = navController,
                                        currentRoute = currentRoute ?: "",
                                        authManager = authManager,
                                        closeDrawer = {
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        })

                                }

                                Role.Admin -> {}
                            }
                        }

                    }
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                if (!(currentRoute == Screen.SignIn.route || currentRoute == Screen.SignUp.route || currentRoute == Screen.Landing.route)) {
                    Appbar(
                        navController = navController,
                        displayBackButton = hasNavPreviousBackStack,
                        onNavigationIconClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                    )
                }
            },
        ) { innerPadding ->
            val containerModifier = Modifier.background(MaterialTheme.colorScheme.background)

            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp),
            ) {
                composable(Screen.Landing.route) {
                    LandingScreen(
                        modifier = containerModifier,
                        navController = navController
                    )
                }
                composable(Screen.SignIn.route) {
                    SignInScreen(
                        modifier = containerModifier,
                        navController = navController,
                    )
                }

                composable(Screen.SignUp.route) {
                    SignUpScreen(
                        modifier = containerModifier,
                        navController = navController
                    )
                }

                composable(Screen.Setting.route) {
                    SettingScreen(
                        modifier = containerModifier,
                    )
                }

                composable(Screen.Home.route) {
                    HomeScreen(
                        modifier = containerModifier,
                    )
                }

                composable(Screen.StudentDashboard.route) {
                    StudentDashboardScreen(
                        modifier = containerModifier,
                    )
                }

                composable(Screen.StudentQuiz.route) {
                    StudentQuizScreen(
                        modifier = containerModifier,
                        navController = navController,
                    )
                }

                composable(
                    Screen.StudentQuizDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) {
                    val quizId = it.arguments?.getString("id") ?: ""
                    StudentQuizDetailScreen(
                        modifier = containerModifier,
                        quizId = quizId,
                    )
                }
            }
        }
    }
}
