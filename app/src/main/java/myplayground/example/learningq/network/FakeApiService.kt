package myplayground.example.learningq.network

import kotlinx.coroutines.delay
import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.Role
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.request.AdminStudentRequest
import myplayground.example.learningq.network.request.LoginRequest
import myplayground.example.learningq.network.response.LoginResponse
import myplayground.example.learningq.network.utils.WithCourses
import myplayground.example.learningq.network.utils.WithPagination
import kotlin.math.ceil

class FakeApiService : ApiService {
    override suspend fun login(body: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun fetchStudentQuiz(page: Int, limit: Int): WithPagination<List<Quiz>> {
        delay(1500)

        val startIndex = minOf((page - 1) * limit, QUIZ_LIST.size)
        val endIndex = minOf(startIndex + limit, QUIZ_LIST.size)

        return WithPagination(
            data = QUIZ_LIST.subList(startIndex, endIndex),
            page = page,
            status = "success",
            totalPage = ceil(QUIZ_LIST.size.toFloat() / limit.toFloat()).toInt(),
        )
    }

    override suspend fun fetchStudentClasses(
        page: Int,
        limit: Int
    ): WithPagination<WithCourses<List<Class>>> {
        delay(1500)

        val startIndex = minOf((page - 1) * limit, CLASS_LIST.size)
        val endIndex = minOf(startIndex + limit, CLASS_LIST.size)

        return WithPagination(
            data = WithCourses(
                courses = CLASS_LIST.subList(startIndex, endIndex),
            ),
            page = page,
            status = "success",
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
            status = "success",
            totalPage = ceil(QUIZ_LIST.size.toFloat() / limit.toFloat()).toInt(),
        )
    }

    override suspend fun createStudent(body: AdminStudentRequest) {
        TODO("Not yet implemented")
    }


    override suspend fun fetchUser(page: Int, limit: Int): WithPagination<List<User>> {
        delay(1500)

        val startIndex = minOf((page - 1) * limit, USER_LIST.size)
        val endIndex = minOf(startIndex + limit, USER_LIST.size)

        return WithPagination(
            data = USER_LIST.subList(startIndex, endIndex),
            page = page,
            status = "success",
            totalPage = ceil(USER_LIST.size.toFloat() / limit.toFloat()).toInt(),
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
                    id = i,
                    courseId = "asdkoasd $i",
                    courseName = "Class $i",
                    instructorId = i,
                    schedule = "",
                    description = "",
                )
                classList.add(`class`)
            }

            classList.toList()
        }

        val USER_LIST: List<User> by lazy {

            val userList = mutableListOf<User>()
            for (i in 1..30) {
                val user = User(
                    id = "$i",
                    name = "User $i",
                    role = Role.Student,
                    image_url = null,
                )
                userList.add(user)
            }

            userList.toList()
        }

        fun getInstance(): FakeApiService = instance ?: synchronized(this) {
            FakeApiService(
            ).apply {
                instance = this
            }
        }
    }
}