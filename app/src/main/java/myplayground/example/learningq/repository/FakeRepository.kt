package myplayground.example.learningq.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myplayground.example.learningq.model.Token

class FakeRepository(context: Context) : Repository {

    override fun userLogin(request: UserLoginInput): Flow<Token?> {
        val existingAccount = UserLoginInput(
            username = "admin",
            password = "pass",
        )

        return flow {

            if (request.username == existingAccount.username && request.password == existingAccount.password) {
                emit(
                    Token(
                        auth_token = "token",
                    )
                )
            } else {
                emit(null)
            }
        }
    }

    override fun userRegister(request: UserRegisterInput): Flow<Token?> {
        return flow {
            emit(null)
        }
    }

    companion object {
        @Volatile
        private var instance: FakeRepository? = null

        fun getInstance(context: Context): FakeRepository = instance ?: synchronized(this) {
            FakeRepository(context).apply {
                instance = this
            }
        }
    }
}
