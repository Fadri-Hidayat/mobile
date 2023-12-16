package myplayground.example.learningq.network

import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.request.LoginRequest
import myplayground.example.learningq.network.response.LoginResponse
import myplayground.example.learningq.network.utils.WithPagination
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest,
    ): LoginResponse

    suspend fun fetchStudentQuiz(
        page: Int,
        limit: Int,
    ): WithPagination<List<Quiz>>

    suspend fun fetchStudentClasses(
        page: Int,
        limit: Int,
    ): WithPagination<List<Class>>

    suspend fun fetchTeacherQuiz(
        page: Int,
        limit: Int,
    ): WithPagination<List<Quiz>>
}