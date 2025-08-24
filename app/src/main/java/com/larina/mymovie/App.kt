package com.larina.mymovie

import MainRepository
import android.app.Application
import com.larina.mymovie.data.Entity.API.ApiConstants
import com.larina.mymovie.data.Entity.TmdbApi
import com.larina.mymovie.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    lateinit var repo: MainRepository
    lateinit var interactor: Interactor
    lateinit var retrofitService: TmdbApi

    override fun onCreate() {
        super.onCreate()
        instance = this
        repo = MainRepository()
        retrofitService = createRetrofitService()
        interactor = Interactor(repo, retrofitService)
    }

    private fun createRetrofitService(): TmdbApi {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(TmdbApi::class.java)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
