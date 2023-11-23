package myplayground.example.learningq.di

import android.content.Context
import myplayground.example.learningq.repository.FakeRepository
import myplayground.example.learningq.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        return FakeRepository.getInstance(context)
    }
}