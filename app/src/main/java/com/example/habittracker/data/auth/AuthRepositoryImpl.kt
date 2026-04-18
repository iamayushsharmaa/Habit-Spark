package com.example.habittracker.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {


    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            val result = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val uid = result.user?.uid
                ?: return AuthResult.Error("User created failed")

            val user = UserData(
                userId = uid,
                name = name,
                email = email,
            )

            firestore.collection("users")
                .document(uid)
                .set(user)
                .await()

            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Signup failed")
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Signin failed")
        }
    }

    override suspend fun signInWithGoogle(idToken: String): AuthResult<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)

            val result = firebaseAuth
                .signInWithCredential(credential)
                .await()

            val user = result.user
                ?: return AuthResult.Error("Google sign-in failed")

            val uid = user.uid

            val userDoc = firestore.collection("users")
                .document(uid)
                .get()
                .await()

            if (!userDoc.exists()) {
                val newUser = UserData(
                    userId = uid,
                    name = user.displayName,
                    email = user.email ?: ""
                )

                firestore.collection("users")
                    .document(uid)
                    .set(newUser)
                    .await()
            }

            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Google sign-in failed")
        }
    }

    override suspend fun getUserData(): AuthResult<UserData?> {
        return try {

            val uid = firebaseAuth.currentUser?.uid
                ?: return AuthResult.Error("User not logged in")

            val document = firestore.collection("users")
                .document(uid)
                .get()
                .await()

            val user = document.toObject(UserData::class.java)

            AuthResult.Success(user)

        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Can not load data")
        }
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

}