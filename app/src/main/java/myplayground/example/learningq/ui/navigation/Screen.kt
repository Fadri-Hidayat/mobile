package myplayground.example.learningq.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Landing : Screen("landing")
    object Setting : Screen("setting")

    object AuthLoading : Screen("authloading")


    object StudentDashboard : Screen("studentdashboard")
    object StudentProfile : Screen("studentprofile")
    object StudentQuiz : Screen("studentquiz")
    object StudentReport : Screen("studentreport")
    object StudentReportDetail : Screen("studentreportdetail")

    object StudentQuizDetail : Screen("studentquiz/{id}") {
        fun createRoute(id: String) = "studentquiz/$id"
    }

    object StudentPresence : Screen("studentpresence")
    object StudentPresenceDetail : Screen("studentpresence/{id}") {
        fun createRoute(classId: String) = "studentpresence/$classId"
    }

    object StudentFeedback : Screen("studentfeedback")

    object TeacherProfile : Screen("teacherprofile")
    object TeacherDashboard : Screen("teacherdashboard")
    object TeacherQuiz : Screen("teacherquiz")
    object TeacherQuizAdd : Screen("teacherquizadd")


    object AdminDashboard : Screen("admindashboard")
    object AdminUser : Screen("adminuser")
    object AdminUserAdd : Screen("adminuseradd")
    object AdminProfile : Screen("adminprofile")
}
