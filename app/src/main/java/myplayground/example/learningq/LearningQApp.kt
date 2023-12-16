package myplayground.example.learningq

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.model.Role
import myplayground.example.learningq.ui.layout.Appbar
import myplayground.example.learningq.ui.layout.DrawerBodyAdmin
import myplayground.example.learningq.ui.layout.DrawerBodyStudent
import myplayground.example.learningq.ui.layout.DrawerBodyTeacher
import myplayground.example.learningq.ui.layout.DrawerHeader
import myplayground.example.learningq.ui.navigation.Screen
import myplayground.example.learningq.ui.screens.admin.dashboard.AdminDashboardScreen
import myplayground.example.learningq.ui.screens.admin.profile.AdminProfileScreen
import myplayground.example.learningq.ui.screens.admin.user.AdminUserScreen
import myplayground.example.learningq.ui.screens.home.HomeScreen
import myplayground.example.learningq.ui.screens.landing.LandingScreen
import myplayground.example.learningq.ui.screens.setting.SettingScreen
import myplayground.example.learningq.ui.screens.sign_in.SignInScreen
import myplayground.example.learningq.ui.screens.sign_up.SignUpScreen
import myplayground.example.learningq.ui.screens.student.dashboard.StudentDashboardScreen
import myplayground.example.learningq.ui.screens.student.feedback.StudentFeedbackScreen
import myplayground.example.learningq.ui.screens.student.presence.StudentPresenceScreen
import myplayground.example.learningq.ui.screens.student.profile.StudentProfileScreen
import myplayground.example.learningq.ui.screens.student.quiz.StudentQuizScreen
import myplayground.example.learningq.ui.screens.student.quiz_detail.StudentQuizDetailScreen
import myplayground.example.learningq.ui.screens.student.report.StudentReportScreen
import myplayground.example.learningq.ui.screens.student.report_detail.StudentReportDetailScreen
import myplayground.example.learningq.ui.screens.teacher.dashboard.TeacherDashboardScreen
import myplayground.example.learningq.ui.screens.teacher.profile.TeacherProfileScreen
import myplayground.example.learningq.ui.screens.teacher.quiz.TeacherQuizScreen
import myplayground.example.learningq.ui.screens.teacher.quiz_add.TeacherQuizAddScreen
import myplayground.example.learningq.ui.utils.debugPlaceholder

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
        if (isLoading.value) {
            Screen.AuthLoading.route
        } else {
            when (user.value?.role) {
                is Role.Student -> {
                    Screen.StudentDashboard.route
                }

                is Role.Teacher -> {
                    Screen.TeacherDashboard.route
                }

                is Role.Admin -> {
                    Screen.AdminDashboard.route
                }

                else -> ""
            }
        }
    } else {
        Screen.Landing.route
    }

    ModalNavigationDrawer(
        gesturesEnabled = !hasNavPreviousBackStack,
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
                                        modifier = Modifier
                                            .weight(1F)
                                            .verticalScroll(rememberScrollState()),
                                        navController = navController,
                                        currentRoute = currentRoute ?: "",
                                        authManager = authManager,
                                        closeDrawer = {
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        },
                                    )
                                }

                                Role.Teacher -> {
                                    DrawerBodyTeacher(
                                        modifier = Modifier
                                            .weight(1F)
                                            .verticalScroll(rememberScrollState()),
                                        navController = navController,
                                        currentRoute = currentRoute ?: "",
                                        authManager = authManager,
                                        closeDrawer = {
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        },
                                    )
                                }

                                Role.Admin -> {
                                    DrawerBodyAdmin(
                                        modifier = Modifier
                                            .weight(1F)
                                            .verticalScroll(rememberScrollState()),
                                        navController = navController,
                                        currentRoute = currentRoute ?: "",
                                        authManager = authManager,
                                        closeDrawer = {
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        },

                                        )
                                }
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
                    ) {
                        val displayProfile = listOf(
                            Screen.StudentDashboard.route,
                            Screen.StudentPresence.route,
                            Screen.StudentQuiz.route,

                            Screen.TeacherDashboard.route,
                        ).contains(currentRoute)

                        if (displayProfile) {
                            Row(
                                modifier = Modifier.fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = user.value?.name ?: "",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.headlineSmall,
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                AsyncImage(
                                    model = user.value?.image_url,
                                    contentDescription = "Profile Photo",
                                    placeholder = debugPlaceholder(debugPreview = R.drawable.avatar_placeholder),
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .width(40.dp)
                                        .clip(CircleShape),
                                )
                            }
                        }
                    }
                }
            },
        ) { innerPadding ->
            val containerModifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)

            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(Screen.AuthLoading.route) { // empty
                }

                composable(Screen.Landing.route) {
                    LandingScreen(
                        modifier = containerModifier, navController = navController
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
                        modifier = containerModifier, navController = navController
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

                composable(Screen.StudentProfile.route) {
                    StudentProfileScreen()
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

                composable(Screen.StudentPresence.route) {
                    StudentPresenceScreen(
                        modifier = containerModifier,
                    )
                }

                composable(Screen.StudentReport.route) {
                    StudentReportScreen(
                        modifier = containerModifier,
                        navController = navController,
                    )
                }

                composable(Screen.StudentReportDetail.route) {
                    StudentReportDetailScreen(
                        modifier = containerModifier,
                        navController = navController,
                    )
                }

                composable(Screen.StudentFeedback.route) {
                    StudentFeedbackScreen(
                        modifier = containerModifier,
                    )
                }

                composable(Screen.TeacherDashboard.route) {
                    TeacherDashboardScreen(
                        modifier = containerModifier,
                    )
                }

                composable(Screen.TeacherProfile.route) {
                    TeacherProfileScreen(
                        modifier = containerModifier,
                    )
                }

                composable(Screen.TeacherQuiz.route) {
                    TeacherQuizScreen(
                        modifier = containerModifier,
                        navController = navController,
                    )
                }


                composable(Screen.TeacherQuizAdd.route) {
                    TeacherQuizAddScreen(
                        modifier = containerModifier
                    )
                }

                composable(Screen.AdminDashboard.route) {
                    AdminDashboardScreen(
                        modifier = containerModifier
                    )
                }

                composable(Screen.AdminProfile.route) {
                    AdminProfileScreen(
                        modifier = containerModifier
                    )
                }

                composable(Screen.AdminUser.route) {
                    AdminUserScreen(
                        modifier = containerModifier
                    )
                }
            }
        }
    }
}
