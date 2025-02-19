package com.example.habittracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.habittracker.data.auth.AuthApi
import com.example.habittracker.data.auth.AuthRepository
import com.example.habittracker.data.auth.AuthRepositoryImpl
import com.example.habittracker.data.calander.CalendarRepository
import com.example.habittracker.data.remote.HabitApi
import com.example.habittracker.data.remote.HabitsRepository
import com.example.habittracker.data.remote.HabitsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRetorfit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.3:8080/")
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
    fun provideHabitRepository(habitApi: HabitApi): HabitsRepository {
        return HabitsRepositoryImpl(habitApi)
    }

    @Provides
    @Singleton
    fun provideCalendarRepository(): CalendarRepository {
        return CalendarRepository()
    }
}