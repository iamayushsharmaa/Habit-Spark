package com.example.habittracker.repository.FirestoreRepository

import android.util.Log
import com.example.habittracker.data.HabitData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class FirestoreRepositoryImpl(): FirestoreRepository {

    private val firestoreDb = Firebase.firestore

    override suspend fun addDataToFirestore(habitData: HabitData): Boolean {
        return try {
            firestoreDb.collection("Habits")
                .add(habitData)
                .await()
            Log.d("addindb", "addDataToFirestore:added ")
            true
        } catch (e: Exception) {
            Log.d("addindb", "error : not added ")
            false
        }
    }

    override suspend fun getDataFromFirestore(): List<HabitData> {
        return try {
            val snapshot = firestoreDb.collection("Habits").get().await()
            snapshot.toObjects(HabitData::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }


}