package myplayground.example.learningq.repository

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myplayground.example.learningq.model.Role
import myplayground.example.learningq.model.Token
import myplayground.example.learningq.model.User

class FakeRepository(context: Context) : Repository {

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

        fun getInstance(context: Context): FakeRepository = instance ?: synchronized(this) {
            FakeRepository(context).apply {
                instance = this
            }
        }
    }
}
