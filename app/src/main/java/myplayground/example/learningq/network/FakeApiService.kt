package myplayground.example.learningq.network

import android.util.Log
import kotlinx.coroutines.delay
import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.utils.WithPagination
import retrofit2.Retrofit
import kotlin.math.ceil

class FakeApiService : ApiService {
    override suspend fun fetchStudentQuiz(page: Int, limit: Int): WithPagination<List<Quiz>> {
        delay(1500)

        val startIndex = minOf((page - 1) * limit, QUIZ_LIST.size)
        val endIndex = minOf(startIndex + limit, QUIZ_LIST.size)

        return WithPagination(
            data = QUIZ_LIST.subList(startIndex, endIndex),
            page = page,
            totalPage = ceil(QUIZ_LIST.size.toFloat() / limit.toFloat()).toInt(),
        )
    }

    override suspend fun fetchStudentClasses(page: Int, limit: Int): WithPagination<List<Class>> {
        delay(1500)

        val startIndex = minOf((page - 1) * limit, CLASS_LIST.size)
        val endIndex = minOf(startIndex + limit, CLASS_LIST.size)

        return WithPagination(
            data = CLASS_LIST.subList(startIndex, endIndex),
            page = page,
            totalPage = ceil(CLASS_LIST.size.toFloat() / limit.toFloat()).toInt(),
        )
    }

    override suspend fun fetchTeacherQuiz(page: Int, limit: Int): WithPagination<List<Quiz>> {
        delay(1500)

        val startIndex = minOf((page - 1) * limit, QUIZ_LIST.size)
        val endIndex = minOf(startIndex + limit, QUIZ_LIST.size)

        return WithPagination(
            data = QUIZ_LIST.subList(startIndex, endIndex),
            page = page,
            totalPage = ceil(QUIZ_LIST.size.toFloat() / limit.toFloat()).toInt(),
        )
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

        val CLASS_LIST: List<Class> by lazy {

            val classList = mutableListOf<Class>()
            for (i in 1..30) {
                val `class` = Class(
                    id = "$i",
                    name = "Class $i",
                )
                classList.add(`class`)
            }

            classList.toList()
        }

        fun getInstance(): FakeApiService = instance ?: synchronized(this) {
            FakeApiService(
            ).apply {
                instance = this
            }
        }
    }
}