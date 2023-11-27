package myplayground.example.learningq.repository

import kotlinx.coroutines.flow.Flow
import myplayground.example.learningq.model.Token

interface Repository {
    fun userLogin(request: UserLoginInput): Flow<Token?>
    fun userRegister(request: UserRegisterInput): Flow<Token?>
}