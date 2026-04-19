package com.example.habittracker.di

import com.example.habittracker.data.auth.AuthRepository
import com.example.habittracker.data.auth.AuthRepositoryImpl
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.data.repository.HabitsRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun provideHabitRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): HabitsRepository {
        return HabitsRepositoryImpl(firebaseAuth, firestore)
    }

}