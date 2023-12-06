package myplayground.example.learningq.network

import kotlinx.coroutines.delay
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.network.utils.WithPagination
import kotlin.math.ceil

class FakeApiService : ApiService {
    override suspend fun fetchStudentQuiz(page: Int, limit: Int): WithPagination<List<Quiz>> {
        delay(1500)

        val startIndex = (page - 1) * limit
        val endIndex = minOf(startIndex + limit, QUIZ_LIST.size)

        return WithPagination(
            data = QUIZ_LIST.subList(startIndex, endIndex),
            page = page,
            totalPage = ceil(QUIZ_LIST.size.toFloat() / limit.toFloat()).toInt(),
        )
        //        return object : List<Quiz> {
        //            override fun enqueue(callback: Callback<List<Quiz>>) {
        //                Handler(Looper.getMainLooper()).postDelayed(
        //                    {
        //                        callback.onResponse(
        //                            this,
        //                            Response.success(QUIZ_LIST.subList(startIndex, endIndex))
        //                        )
        //                    },
        //                    1500,
        //                )
        //            }
        //
        //
        //            override fun clone(): Call<List<Quiz>> = this
        //
        //            override fun execute(): Response<List<Quiz>> =
        //                Response.success(QUIZ_LIST.subList(startIndex, endIndex))
        //
        //
        //            override fun isExecuted() = false
        //
        //            override fun cancel() {}
        //
        //            override fun isCanceled() = false
        //
        //            override fun request(): Request = Request.Builder().build()
        //
        //
        //        }
    }

    companion object {
        @Volatile
        private var instance: FakeApiService? = null

        val QUIZ_LIST: List<Quiz> by lazy {

            val quizList = mutableListOf<Quiz>()
            for (i in 1..30) {
                val quiz = Quiz(
                    id = "$i",
                    name = "Quiz $i",
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