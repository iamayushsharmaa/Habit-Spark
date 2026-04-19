package com.example.habittracker.data.repository

import com.example.habittracker.data.models.HabitCompletionDto
import com.example.habittracker.data.models.HabitDto
import com.example.habittracker.data.models.HabitRequest
import com.example.habittracker.data.models.HabitResponse
import com.example.habittracker.data.models.Resource
import com.example.habittracker.mappers.toDomain
import com.example.habittracker.mappers.toDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class HabitsRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : HabitsRepository {
    override suspend fun createHabit(habit: HabitRequest) {
        val userId = firebaseAuth.currentUser?.uid ?: return

        val habitId = UUID.randomUUID().toString()

        val dto = habit.toDto(habitId)

        firestore.collection("users")
            .document(userId)
            .collection("habits")
            .document(habitId)
            .set(dto)
            .await()
    }

    override fun getHabits(): Flow<Resource<List<HabitResponse>>> = callbackFlow {
        trySend(Resource.Loading)

        val userId = firebaseAuth.currentUser?.uid ?: return@callbackFlow

        val listener = firestore.collection("users")
            .document(userId)
            .collection("habits")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Error"))
                    return@addSnapshotListener
                }

                val habits = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(HabitDto::class.java)?.toDomain()
                } ?: emptyList()

                trySend(Resource.Success(habits))
            }

        awaitClose { listener.remove() }
    }

    override suspend fun toggleHabitCompletion(habitId: String, date: Long) {
        val userId = firebaseAuth.currentUser?.uid ?: return

        val habitRef = firestore.collection("users")
            .document(userId)
            .collection("habits")
            .document(habitId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(habitRef)
            val dto = snapshot.toObject(HabitDto::class.java) ?: return@runTransaction

            val updatedHistory = dto.completionHistory.toMutableList()

            val index = updatedHistory.indexOfFirst {
                it.date == date
            }

            if (index >= 0) {
                updatedHistory.removeAt(index)
            } else {

                updatedHistory.add(
                    HabitCompletionDto(date = date, isCompleted = true)
                )
            }
            transaction.update(habitRef, "completionHistory", updatedHistory)
        }.await()
    }

    override suspend fun deleteHabit(habitId: String) {
        val userId = firebaseAuth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(userId)
            .collection("habits")
            .document(habitId)
            .delete()
            .await()
    }

}