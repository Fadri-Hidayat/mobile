package myplayground.example.learningq.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.Token
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.utils.WithPagination
import retrofit2.Call

interface Repository {
    suspend fun userLogin(request: UserLoginInput): Token?
    fun userRegister(request: UserRegisterInput): Flow<Token?>
    fun userMe(token: String): User?

    suspend fun fetchQuiz(page: Int, limit: Int): Flow<PagingData<Quiz>>
}