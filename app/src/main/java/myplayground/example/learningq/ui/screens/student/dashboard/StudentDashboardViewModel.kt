package myplayground.example.learningq.ui.screens.student.dashboard

import androidx.lifecycle.ViewModel
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.utils.AuthManager

class StudentDashboardViewModel(
    private val repository: Repository,
    val authManager: AuthManager,
) :
    ViewModel() {
}