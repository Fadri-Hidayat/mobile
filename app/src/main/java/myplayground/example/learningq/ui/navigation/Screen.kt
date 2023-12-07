package myplayground.example.learningq.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Landing : Screen("landing")
    object Setting : Screen("setting")

    object AuthLoading: Screen("authloading")


    object StudentDashboard : Screen("studentdashboard")
    object StudentProfile : Screen("studentprofile")
    object StudentQuiz : Screen("studentquiz")

    object StudentQuizDetail : Screen("studentquiz/{id}") {
        fun createRoute(id: String) = "studentquiz/$id"
    }

    object StudentPresence : Screen("studentpresence")
}