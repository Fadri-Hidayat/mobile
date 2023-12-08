package myplayground.example.learningq.ui.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import myplayground.example.learningq.ThemeViewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.ui.screens.home.HomeViewModel
import myplayground.example.learningq.ui.screens.sign_in.SignInViewModel
import myplayground.example.learningq.ui.screens.sign_up.SignUpViewModel
import myplayground.example.learningq.ui.screens.student.dashboard.StudentDashboardViewModel
import myplayground.example.learningq.ui.screens.student.presence.StudentPresenceViewModel
import myplayground.example.learningq.ui.screens.student.profile.StudentProfileViewModel
import myplayground.example.learningq.ui.screens.student.quiz.StudentQuizViewModel
import myplayground.example.learningq.ui.screens.student.quiz_detail.StudentQuizDetailViewModel
import myplayground.example.learningq.ui.screens.teacher.dashboard.TeacherDashboardViewModel
import myplayground.example.learningq.ui.screens.teacher.quiz.TeacherQuizViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val application: Application,
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val authManager = Injection.provideAuthManager(application.applicationContext)

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository, authManager) as T
        } else if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel.getInstance(localStorageManager) as T
        } else if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(StudentDashboardViewModel::class.java)) {
            return StudentDashboardViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(StudentQuizViewModel::class.java)) {
            return StudentQuizViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(StudentQuizDetailViewModel::class.java)) {
            return StudentQuizDetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(StudentPresenceViewModel::class.java)) {
            return StudentPresenceViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(StudentProfileViewModel::class.java)) {
            return StudentProfileViewModel(authManager) as T
        } else if (modelClass.isAssignableFrom(TeacherDashboardViewModel::class.java)) {
            return TeacherDashboardViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(TeacherQuizViewModel::class.java)) {
            return TeacherQuizViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}