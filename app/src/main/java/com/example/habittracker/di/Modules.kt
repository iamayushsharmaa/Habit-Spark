package com.example.habittracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.habittracker.data.auth.AuthApi
import com.example.habittracker.data.auth.AuthRepository
import com.example.habittracker.data.auth.AuthRepositoryImpl
import com.example.habittracker.data.remote.HabitApi
import com.example.habittracker.data.remote.HabitsRepository
import com.example.habittracker.data.remote.HabitsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRetorfit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Increase timeout to 30s
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Log requests/responses
            })
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHabitApi(retrofit: Retrofit): HabitApi {
        return retrofit.create(HabitApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences{
        return app.getSharedPreferences("sharedPrefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, sharedPrefs: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, sharedPrefs)
    }

    @Provides
    @Singleton
    fun provideHabitRepository(habitApi: HabitApi, sharedPrefs: SharedPreferences): HabitsRepository {
        return HabitsRepositoryImpl(habitApi,sharedPrefs)
    }

    @Provides
    @Singleton
    fun provideCalendarRepository(): CalendarRepository {
        return CalendarRepository()
    }
}