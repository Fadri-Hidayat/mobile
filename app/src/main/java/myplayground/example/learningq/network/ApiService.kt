package myplayground.example.learningq.network

import myplayground.example.learningq.model.Quiz
import retrofit2.Call

interface ApiService {
    fun fetchStudentQuiz(
        page: Int,
        limit: Int,
    ): Call<List<Quiz>>
}