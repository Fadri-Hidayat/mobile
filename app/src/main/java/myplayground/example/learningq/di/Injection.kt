package myplayground.example.learningq.di

import android.content.Context
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.network.ApiService
import myplayground.example.learningq.network.FakeApiService
import myplayground.example.learningq.network.NetworkConfig
import myplayground.example.learningq.repository.FakeRepository
import myplayground.example.learningq.repository.LearningQRepository
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.utils.AuthManager

object Injection {
    fun provideRepository(context: Context): Repository {
        return LearningQRepository.getInstance(
            context,
        )
    }

    fun provideFakeRepository(context: Context): Repository {
        return FakeRepository.getInstance(
            context,
        )
    }

    fun provideAuthManager(context: Context): AuthManager {
        return AuthManager.getInstance(
            provideRepository(context),
            DatastoreSettings.getInstance(context.dataStore),
        )
    }

    fun provideApiService(localStorageManager: LocalStorageManager): ApiService {
        return NetworkConfig.create(NetworkConfig.ApiBaseUrl, localStorageManager)
    }

    fun provideFakeApiService(): ApiService {
        return FakeApiService.getInstance()
    }
}