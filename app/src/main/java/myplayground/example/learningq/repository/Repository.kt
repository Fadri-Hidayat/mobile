package myplayground.example.learningq.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.Token
import myplayground.example.learningq.model.User

interface Repository {
    suspend fun userLogin(request: UserLoginInput): Token?
    fun userRegister(request: UserRegisterInput): Flow<Token?>
    suspend fun userMe(token: String): User?

    suspend fun fetchStudentQuizPaging(): Flow<PagingData<Quiz>>
    suspend fun fetchStudentClassPaging(): Flow<PagingData<Class>>

    suspend fun fetchTeacherQuizPaging(): Flow<PagingData<Quiz>>
}