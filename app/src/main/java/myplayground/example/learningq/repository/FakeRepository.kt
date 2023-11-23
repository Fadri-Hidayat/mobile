package myplayground.example.learningq.repository

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myplayground.example.learningq.R
import myplayground.example.learningq.model.News
import myplayground.example.learningq.utils.JsonHelper

class FakeRepository(context: Context) : Repository {
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
