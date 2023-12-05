package myplayground.example.learningq.repository

import kotlinx.coroutines.flow.Flow
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.Token
import myplayground.example.learningq.model.User
import retrofit2.Call

interface Repository {
    suspend fun userLogin(request: UserLoginInput): Token?
    fun userRegister(request: UserRegisterInput): Flow<Token?>
    fun userMe(token: String): User?

    fun fetchQuiz(page: Int, limit: Int): Call<List<Quiz>>
}