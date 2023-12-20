package myplayground.example.learningq.network

import kotlinx.coroutines.delay
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Course
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.Role
import myplayground.example.learningq.model.Token
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.request.AdminCreateUserRequest
import myplayground.example.learningq.network.request.LoginRequest
import myplayground.example.learningq.network.request.StudentCourseFetchRequest
import myplayground.example.learningq.network.response.LoginResponse
import myplayground.example.learningq.network.response.MeResponse
import myplayground.example.learningq.network.utils.WithCourses
import myplayground.example.learningq.network.utils.WithPagination
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import kotlin.math.ceil

class FakeApiService(val localStorageManager: LocalStorageManager) : ApiService {
    override suspend fun login(body: LoginRequest): LoginResponse {
        delay(1500)
        for (currentUser in listUser) {
            if (body.email == currentUser.email && body.password == currentUser.password) {
                return LoginResponse(
                    accessToken = "token ${currentUser.role}",
                    role = currentUser.role.toString(),
                )
            }
        }

        throw HttpException(
            Response.error<Token?>(400, "Invalid User/Password".toResponseBody())
        )
    }

    override suspend fun userMe(): MeResponse {
        delay(500)

        val token = localStorageManager.getUserToken()


        if (token.length <= "token ".length) {
            return MeResponse(
                studentUser,
            )
        }

        return when (token.substring("token ".length)) {
            "student" -> {
                MeResponse(
                    studentUser,
                )
            }

            "teacher" -> {
                MeResponse(
                    teacherUser,
                )

            }

            "admin" -> {
                MeResponse(
                    adminUser,
                )

            }

            "parent" -> {
                MeResponse(
                    parentUser,
                )

            }

            else -> MeResponse(
                studentUser,
            )
        }
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

    override suspend fun fetchStudentCoursesByClassId(body: StudentCourseFetchRequest): WithPagination<List<Course>> {
        delay(1500)

        val (classId, page, limit) = body

        val filteredCourseList = COURSE_LIST.filter { it.classId == classId }

        val startIndex = minOf((page - 1) * limit, filteredCourseList.size)
        val endIndex = minOf(startIndex + limit, filteredCourseList.size)

        return WithPagination(
            data = filteredCourseList.subList(startIndex, endIndex),
            page = page,
            status = "success",
            totalPage = ceil(filteredCourseList.size.toFloat() / limit.toFloat()).toInt(),
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

    override suspend fun createUser(body: AdminCreateUserRequest) {
        TODO("Not yet implemented")
    }


    override suspend fun fetchUser(page: Int, limit: Int): WithPagination<List<User>> {
        delay(1500)

        val startIndex = minOf((page - 1) * limit, listUser.size)
        val endIndex = minOf(startIndex + limit, listUser.size)

        return WithPagination(
            data = listUser.subList(startIndex, endIndex),
            page = page,
            status = "success",
            totalPage = ceil(listUser.size.toFloat() / limit.toFloat()).toInt(),
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

            val classList = mutableListOf(
                Class(
                    id = "1",
                    name = "Kelas IX-1"
                ),
                Class(
                    id = "2",
                    name = "Kelas IX-2"
                ),
            )
            classList.toList()
        }

        val COURSE_LIST: List<Course> by lazy {
            val courseList = mutableListOf(
                Course(
                    id = "1",
                    classId = "1",
                    name = "Matematika",
                    teacherUserId = "2",
                    dayOfWeek = 1,
                    startTimeInMinutes = 28800,
                    endTimeInMinutes = 54000,
                    description = null,
                ),
                Course(
                    id = "2",
                    classId = "1",
                    name = "Fisika",
                    teacherUserId = "2",
                    dayOfWeek = 2,
                    startTimeInMinutes = 28800,
                    endTimeInMinutes = 54000,
                    description = null,
                ),
            )

            courseList.toList()
        }


        val studentUser = User(
            id = "1",
            email = "student.one@gmail.com",
            name = "Student",
            password = "12345",
//            image_url = "https://miro.medium.com/v2/resize:fill:110:110/1*x1I-A7aVdqWFelvJakKWBg.jpeg",
            role = Role.Student,
        )

        val teacherUser = User(
            id = "2",
            email = "teacher.one@gmail.com",
            name = "Teacher",
            password = "12345",
//            image_url = "https://miro.medium.com/v2/resize:fill:110:110/1*x1I-A7aVdqWFelvJakKWBg.jpeg",
            role = Role.Teacher,
        )

        val adminUser = User(
            id = "3",
            email = "admin.one@gmail.com",
            name = "Admin",
            password = "12345",
//            image_url = "https://miro.medium.com/v2/resize:fill:110:110/1*x1I-A7aVdqWFelvJakKWBg.jpeg",
            role = Role.Admin,
        )

        val parentUser = User(
            id = "4",
            email = "parent.one@gmail.com",
            name = "Parent",
            password = "12345",
//            image_url = "https://miro.medium.com/v2/resize:fill:110:110/1*x1I-A7aVdqWFelvJakKWBg.jpeg",
            role = Role.Admin,
        )

        val listUser = listOf(
            studentUser,
            teacherUser,
            adminUser,
            parentUser
        )

//        val USER_LIST: List<User> by lazy {
//
//            val userList = mutableListOf<User>()
//            for (i in 1..30) {
//                val user = User(
//                    id = "$i",
//                    name = "User $i",
//                    role = Role.Student,
//                    imageUrl = null,
//                    email = "$i@gmail.com",
//                    password = "12345",
//                )
//                userList.add(user)
//            }
//
//            userList.toList()
//        }

        fun getInstance(
            localStorageManager: LocalStorageManager,
        ): FakeApiService = instance ?: synchronized(this) {
            FakeApiService(
                localStorageManager = localStorageManager,
            ).apply {
                instance = this
            }
        }
    }
}