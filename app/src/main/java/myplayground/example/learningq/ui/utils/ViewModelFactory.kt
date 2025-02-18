package myplayground.example.learningq.ui.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import myplayground.example.learningq.ThemeViewModel
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.repository.FakeRepository
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.ui.screens.admin.dashboard.AdminDashboardViewModel
import myplayground.example.learningq.ui.screens.admin.profile.AdminProfileViewModel
import myplayground.example.learningq.ui.screens.admin.user.AdminUserViewModel
import myplayground.example.learningq.ui.screens.admin.user_add.AdminUserAddViewModel
import myplayground.example.learningq.ui.screens.home.HomeViewModel
import myplayground.example.learningq.ui.screens.sign_in.SignInViewModel
import myplayground.example.learningq.ui.screens.sign_up.SignUpViewModel
import myplayground.example.learningq.ui.screens.student.dashboard.StudentDashboardViewModel
import myplayground.example.learningq.ui.screens.student.feedback.StudentFeedbackViewModel
import myplayground.example.learningq.ui.screens.student.presence.StudentPresenceViewModel
import myplayground.example.learningq.ui.screens.student.presence_detail.StudentPresenceDetailViewModel
import myplayground.example.learningq.ui.screens.student.profile.StudentProfileViewModel
import myplayground.example.learningq.ui.screens.student.quiz.StudentQuizViewModel
import myplayground.example.learningq.ui.screens.student.quiz_detail.StudentQuizDetailViewModel
import myplayground.example.learningq.ui.screens.student.report.StudentReportViewModel
import myplayground.example.learningq.ui.screens.student.report_detail.StudentReportDetailViewModel
import myplayground.example.learningq.ui.screens.teacher.dashboard.TeacherDashboardViewModel
import myplayground.example.learningq.ui.screens.teacher.feedback.TeacherFeedbackViewModel
import myplayground.example.learningq.ui.screens.teacher.feedback_detail.TeacherFeedbackDetailViewModel
import myplayground.example.learningq.ui.screens.teacher.profile.TeacherProfileViewModel
import myplayground.example.learningq.ui.screens.teacher.quiz.TeacherQuizViewModel
import myplayground.example.learningq.ui.screens.teacher.quiz_add.TeacherQuizAddViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val application: Application,
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val fakeRepository = FakeRepository.getInstance(application.applicationContext)

        val authManager = Injection.provideAuthManager(application.applicationContext)

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository, authManager) as T
        } else if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel.getInstance(localStorageManager) as T
        } else if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(
                application.applicationContext,
                repository,
                localStorageManager
            ) as T
        } else if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(StudentDashboardViewModel::class.java)) {
            return StudentDashboardViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(StudentQuizViewModel::class.java)) {
            return StudentQuizViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(StudentQuizDetailViewModel::class.java)) {
            return StudentQuizDetailViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(StudentPresenceViewModel::class.java)) {
            return StudentPresenceViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(StudentPresenceDetailViewModel::class.java)) {
            return StudentPresenceDetailViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(StudentProfileViewModel::class.java)) {
            return StudentProfileViewModel(authManager) as T
        } else if (modelClass.isAssignableFrom(StudentReportViewModel::class.java)) {
            return StudentReportViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(StudentReportDetailViewModel::class.java)) {
            return StudentReportDetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(StudentFeedbackViewModel::class.java)) {
            return StudentFeedbackViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(TeacherDashboardViewModel::class.java)) {
            return TeacherDashboardViewModel(repository, localStorageManager, authManager) as T
        } else if (modelClass.isAssignableFrom(TeacherProfileViewModel::class.java)) {
            return TeacherProfileViewModel(authManager) as T
        } else if (modelClass.isAssignableFrom(TeacherQuizViewModel::class.java)) {
            return TeacherQuizViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(TeacherQuizAddViewModel::class.java)) {
            return TeacherQuizAddViewModel(repository, localStorageManager, authManager) as T
        } else if (modelClass.isAssignableFrom(TeacherFeedbackDetailViewModel::class.java)) {
            return TeacherFeedbackDetailViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(TeacherFeedbackViewModel::class.java)) {
            return TeacherFeedbackViewModel(repository, localStorageManager, authManager) as T
        } else if (modelClass.isAssignableFrom(AdminDashboardViewModel::class.java)) {
            return AdminDashboardViewModel() as T
        } else if (modelClass.isAssignableFrom(AdminUserViewModel::class.java)) {
            return AdminUserViewModel(fakeRepository, localStorageManager) as T
//            return AdminUserViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(AdminUserAddViewModel::class.java)) {
            return AdminUserAddViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AdminProfileViewModel::class.java)) {
            return AdminProfileViewModel(authManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}