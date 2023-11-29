package myplayground.example.learningq.ui.screens.home

import androidx.lifecycle.ViewModel
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.utils.AuthManager

class HomeViewModel(
    private val repository: Repository,
    val authManager: AuthManager,
) :
    ViewModel() {
}