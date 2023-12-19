package myplayground.example.learningq.network

import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.request.AdminCreateUserRequest
import myplayground.example.learningq.network.request.LoginRequest
import myplayground.example.learningq.network.response.LoginResponse
import myplayground.example.learningq.network.utils.WithCourses
import myplayground.example.learningq.network.utils.WithPagination
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest,
    ): LoginResponse

    suspend fun fetchStudentQuiz(
        page: Int,
        limit: Int,
    ): WithPagination<List<Quiz>>


    @GET("/course")
    suspend fun fetchStudentClasses(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): WithPagination<WithCourses<List<Class>>>

    @GET("/")

    suspend fun fetchTeacherQuiz(
        page: Int,
        limit: Int,
    ): WithPagination<List<Quiz>>


    @POST("/register")
    suspend fun createUser(
        @Body body: AdminCreateUserRequest,
    )

    suspend fun fetchUser(page: Int, limit: Int): WithPagination<List<User>>
}