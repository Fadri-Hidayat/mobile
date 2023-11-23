package myplayground.example.learningq.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SignIn : Screen("signin")
}