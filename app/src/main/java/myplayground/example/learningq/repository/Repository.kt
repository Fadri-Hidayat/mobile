package myplayground.example.learningq.repository

import kotlinx.coroutines.flow.Flow
import myplayground.example.learningq.model.Token
import myplayground.example.learningq.model.User

interface Repository {
    suspend fun userLogin(request: UserLoginInput): Token?
    fun userRegister(request: UserRegisterInput): Flow<Token?>
    fun userMe(token: String): User?

}