package myplayground.example.learningq.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.model.Role
import myplayground.example.learningq.model.Token
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.ApiService
import myplayground.example.learningq.repository.paging.QuizPagingSource

class FakeRepository(
    context: Context,
    private val apiService: ApiService,
) : Repository {

    override suspend fun userLogin(request: UserLoginInput): Token? {
        delay(1500)

        for (existingAccount in existingAccounts) {
            if (request.username == existingAccount.username && request.password == existingAccount.password) {
                return Token(
                    auth_token = "token",
                )

            }
        }

        return null
    }

    override fun userRegister(request: UserRegisterInput): Flow<Token?> {
        return flow {
            emit(null)
        }
    }

    override fun userMe(token: String): User? {
        return User(
            id = "1",
            name = "Student",
            image_url = "https://miro.medium.com/v2/resize:fill:110:110/1*x1I-A7aVdqWFelvJakKWBg.jpeg",
            role = Role.Student,
        )
    }


    override suspend fun fetchQuiz(page: Int, limit: Int): Flow<PagingData<Quiz>> {
        return Pager(
            config = PagingConfig(pageSize = 5, prefetchDistance = 2),
            pagingSourceFactory = {
                QuizPagingSource(apiService)
            }
        ).flow
    }

    companion object {
        @Volatile
        private var instance: FakeRepository? = null
        private val existingAccounts = listOf(
            UserLoginInput(
                username = "admin",
                password = "pass",
            ),
            UserLoginInput(
                username = "student",
                password = "pass",
            ),
        )

        fun getInstance(
            context: Context,
            apiService: ApiService,
        ): FakeRepository = instance ?: synchronized(this) {
            FakeRepository(
                context,
                apiService,
            ).apply {
                instance = this
            }
        }
    }
}
