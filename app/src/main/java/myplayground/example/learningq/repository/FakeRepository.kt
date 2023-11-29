package myplayground.example.learningq.repository

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myplayground.example.learningq.model.Token
import myplayground.example.learningq.model.User

class FakeRepository(context: Context) : Repository {

    override fun userLogin(request: UserLoginInput): Token? {
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
            name = "Student"
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
