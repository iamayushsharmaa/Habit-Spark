package com.example.habittracker.data.auth

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.auth.User
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sharedPrefs: SharedPreferences
): AuthRepository {
    private var currentUser: UserData? = null

    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            signIn(username, password)
        } catch(e: HttpException){
            when (e.code()) {
                401 -> AuthResult.Unauthorized(e.message)
                else -> AuthResult.UnknownError(e.message)
            }
        } catch (e: Exception){
            AuthResult.UnknownError(e.message ?: "Network error")
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )

            Log.d("TAG", "signIn: ${response.token} ")
            sharedPrefs.edit()
                .putString("jwt", response.token)
                .apply()

            AuthResult.Authorized()
        } catch(e: HttpException){
            if (e.code() == 401){
                AuthResult.Unauthorized()
            } else{
                AuthResult.UnknownError()
            }
        } catch (e: Exception){
            AuthResult.UnknownError()
        }
    }

    override suspend fun getUserData(): AuthResult<UserData?> {
        try {
            val token = sharedPrefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            val result = api.getUserInfo(token)
            if (result != null){
                return AuthResult.Authorized(result)
            }else{
                return AuthResult.Unauthorized()
            }
        }catch (e: HttpException){
            if (e.code() == 401){
                return AuthResult.Unauthorized()
            } else{
                return AuthResult.UnknownError()
            }
        }catch (e: Exception){
            return AuthResult.UnknownError()
        }
    }

//    override suspend fun updateUserName(newName: String): AuthResult<Unit> {
//        val token = sharedPrefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
//        val user = currentUser?.copy(name = newName) ?: return AuthResult.Unauthorized("User not logged in")
//            val response = api.updateUser(token, user)
//            if (response.isSuccessful) {
//                currentUser = user
//                return AuthResult.Success(Unit)
//            }
//        }
//        return AuthResult.UnknownError("Failed to update name")
//    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            Log.d("TAG", "authentication going on ")

            val token = sharedPrefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate(token)
            Log.d("TAG", "authenticated")

            AuthResult.Authorized()
        } catch(e: HttpException){
            if (e.code() == 401){
                Log.d("TAG", "unauthorized error ${e.message}")

                AuthResult.Unauthorized(e.message)
            } else{
                Log.d("TAG", "unknown error ${e.message}")
                AuthResult.UnknownError(e.message)
            }
        } catch (e: Exception){
            Log.d("TAG", "unknown error ${e.message}")
            AuthResult.UnknownError(e.message)
        }
    }
}