package myplayground.example.learningq.network

import kotlinx.coroutines.runBlocking
import myplayground.example.learningq.local_storage.LocalStorageManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConfig {
    companion object {
        inline fun <reified T> create(
            baseUrl: String,
            localStorageManager: LocalStorageManager? = null,
        ): T {
            // auth
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val reqBuilder = req.newBuilder()

                if (localStorageManager != null) {
                    var userToken: String = ""
                    runBlocking {
                        userToken = localStorageManager.getUserToken()
                    }

                    reqBuilder.addHeader("authorization", "Bearer $userToken")
                }

                val requestHeaders = reqBuilder.build()
                chain.proceed(requestHeaders)
            }

            val httpClient =
                OkHttpClient.Builder().addInterceptor(authInterceptor)
                    .build()

            val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(T::class.java)
        }
    }
}