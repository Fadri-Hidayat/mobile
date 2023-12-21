package myplayground.example.learningq.network

import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Course
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.QuizQuestion
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.request.AdminCreateUserRequest
import myplayground.example.learningq.network.request.LoginRequest
import myplayground.example.learningq.network.request.StudentCourseFetchRequest
import myplayground.example.learningq.network.response.LoginResponse
import myplayground.example.learningq.network.response.MeResponse
import myplayground.example.learningq.network.utils.WithCourses
import myplayground.example.learningq.network.utils.WithPagination
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest,
    ): LoginResponse

    suspend fun userMe(): MeResponse

    suspend fun fetchStudentQuiz(
        page: Int,
        limit: Int,
    ): WithPagination<List<Quiz>>

    suspend fun fetchStudentQuizQuestion(
        quizId: String,
    ): List<QuizQuestion>

    @GET("/course")
    suspend fun fetchStudentClasses(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): WithPagination<WithCourses<List<Class>>>

    suspend fun fetchStudentCoursesByClassId(
        @Body body: StudentCourseFetchRequest,
    ): WithPagination<List<Course>>

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