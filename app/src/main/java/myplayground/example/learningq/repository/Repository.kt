package myplayground.example.learningq.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myplayground.example.learningq.model.Class
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.Role
import myplayground.example.learningq.model.Token
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.ApiService
import myplayground.example.learningq.network.request.LoginRequest
import myplayground.example.learningq.repository.paging.StudentClassPagingSource
import myplayground.example.learningq.repository.paging.StudentQuizPagingSource
import myplayground.example.learningq.repository.paging.TeacherQuizPagingSource

interface Repository {
    suspend fun userLogin(request: UserLoginInput, apiService: ApiService): Token?
    fun userRegister(request: UserRegisterInput): Flow<Token?>
    suspend fun userMe(token: String): User?

    suspend fun fetchStudentQuizPaging(apiService: ApiService): Flow<PagingData<Quiz>>
    suspend fun fetchStudentClassPaging(apiService: ApiService): Flow<PagingData<Class>>

    suspend fun fetchTeacherQuizPaging(apiService: ApiService): Flow<PagingData<Quiz>>

    suspend fun fetchUserPaging(apiService: ApiService): Flow<PagingData<User>>
}


class LearningQRepository(
    context: Context,
) : Repository {

    override suspend fun userLogin(request: UserLoginInput, apiService: ApiService): Token? {
        val loginResponse = apiService.login(
            LoginRequest(
                request.username,
                request.password,
            ),
        )

        return Token(
            auth_token = loginResponse.accessToken,
            role = loginResponse.role,
        )
    }

    override fun userRegister(request: UserRegisterInput): Flow<Token?> {
        return flow {
            emit(null)
        }
    }

    override suspend fun userMe(token: String): User? {
        delay(1500)

        val studentUser = User(
            id = "1",
            name = "Student",
            image_url = "https://miro.medium.com/v2/resize:fill:110:110/1*x1I-A7aVdqWFelvJakKWBg.jpeg",
            role = Role.Student,
        )

        val teacherUser = User(
            id = "2",
            name = "Teacher",
            image_url = "https://miro.medium.com/v2/resize:fill:110:110/1*x1I-A7aVdqWFelvJakKWBg.jpeg",
            role = Role.Teacher,
        )

        val adminUser = User(
            id = "3",
            name = "Admin",
            image_url = "https://miro.medium.com/v2/resize:fill:110:110/1*x1I-A7aVdqWFelvJakKWBg.jpeg",
            role = Role.Admin,
        )

        if (token.length <= "token ".length) {
            return studentUser
        }

        return when (token.substring("token ".length)) {
            "student" -> {
                studentUser
            }

            "teacher" -> {
                teacherUser
            }

            "admin" -> {
                adminUser
            }

            else -> studentUser
        }
    }

    override suspend fun fetchStudentQuizPaging(apiService: ApiService): Flow<PagingData<Quiz>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                StudentQuizPagingSource(
                    apiService,
                )
            }).flow
    }

    override suspend fun fetchStudentClassPaging(apiService: ApiService): Flow<PagingData<Class>> {
        return Pager(
            config = PagingConfig(
                pageSize = 7,
                initialLoadSize = 7,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                StudentClassPagingSource(
                    apiService,
                )
            }).flow
    }

    override suspend fun fetchTeacherQuizPaging(apiService: ApiService): Flow<PagingData<Quiz>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                TeacherQuizPagingSource(
                    apiService,
                )
            }).flow
    }

    override suspend fun fetchUserPaging(apiService: ApiService): Flow<PagingData<User>> {
        TODO("Not yet implemented")
    }

    companion object {
        @Volatile
        private var instance: LearningQRepository? = null
        private val existingAccounts = listOf(
            UserLoginInput(
                username = "admin",
                password = "pass",
            ),
            UserLoginInput(
                username = "teacher",
                password = "pass",
            ),
            UserLoginInput(
                username = "student",
                password = "pass",
            ),
        )

        fun getInstance(
            context: Context,
        ): LearningQRepository = instance ?: synchronized(this) {
            LearningQRepository(
                context,
            ).apply {
                instance = this
            }
        }
    }
}
