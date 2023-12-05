package myplayground.example.learningq.network

import android.os.Handler
import android.os.Looper
import myplayground.example.learningq.model.Quiz
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FakeApiService : ApiService {
    override fun fetchStudentQuiz(page: Int, limit: Int): Call<List<Quiz>> {
        val startIndex = (page - 1) * limit
        val endIndex = minOf(startIndex + limit, QUIZ_LIST.size)

        return object : Call<List<Quiz>> {
            override fun enqueue(callback: Callback<List<Quiz>>) {
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        callback.onResponse(
                            this,
                            Response.success(QUIZ_LIST.subList(startIndex, endIndex))
                        )
                    },
                    1500,
                )
            }


            override fun clone(): Call<List<Quiz>> = this

            override fun execute(): Response<List<Quiz>> =
                Response.success(QUIZ_LIST.subList(startIndex, endIndex))


            override fun isExecuted() = false

            override fun cancel() {}

            override fun isCanceled() = false

            override fun request(): Request = Request.Builder().build()


        }
    }

    companion object {
        @Volatile
        private var instance: FakeApiService? = null

        val QUIZ_LIST: List<Quiz> by lazy {

            val quizList = mutableListOf<Quiz>()
            for (i in 1..30) {
                val quiz = Quiz(
                    id = "$i",
                    name = "Question $i",
                )
                quizList.add(quiz)
            }

            quizList.toList()
        }


        fun getInstance(): FakeApiService = instance ?: synchronized(this) {
            FakeApiService(
            ).apply {
                instance = this
            }
        }
    }
}