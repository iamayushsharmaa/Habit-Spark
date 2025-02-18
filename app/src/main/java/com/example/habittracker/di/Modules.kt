package com.example.habittracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.habittracker.data.auth.AuthApi
import com.example.habittracker.data.auth.AuthRepository
import com.example.habittracker.data.auth.AuthRepositoryImpl
import com.example.habittracker.data.calander.CalendarRepository
import com.example.habittracker.repository.FirestoreRepository.FirestoreRepository
import com.example.habittracker.repository.FirestoreRepository.FirestoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRetorfit(): AuthApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.3:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
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
    fun provideFirestoreRepository(): FirestoreRepository {
        return FirestoreRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideCalendarRepository(): CalendarRepository {
        return CalendarRepository()
    }
}