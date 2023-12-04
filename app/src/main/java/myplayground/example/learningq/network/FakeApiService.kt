package myplayground.example.learningq.network

import myplayground.example.learningq.model.Quiz
import retrofit2.Call

class FakeApiService : ApiService {
    override fun fetchStudentQuiz(page: Int, limit: Int): Call<List<Quiz>> {
        TODO("Not yet implemented")
    }
}