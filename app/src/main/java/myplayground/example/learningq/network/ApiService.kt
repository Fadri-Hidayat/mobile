package myplayground.example.learningq.network

import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.utils.WithPagination
import retrofit2.Call

interface ApiService {
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