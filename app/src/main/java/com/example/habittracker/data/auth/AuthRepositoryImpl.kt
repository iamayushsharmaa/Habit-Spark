package com.example.habittracker.data.auth

import android.content.SharedPreferences
import android.util.Log
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sharedPrefs: SharedPreferences
): AuthRepository {

    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            Log.d("TAG", "before: response and now sign in ")

            api.signUp(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            Log.d("TAG", "signUp: response and now sign in ")
            signIn(username, password)

        } catch(e: HttpException){
            if (e.code() == 401){
                Log.d("TAG", "signUp: $e.message")
                AuthResult.Unauthorized(e.message)
            } else{
                Log.d("TAG", "signUp: $e.message")
                AuthResult.UnknownError(e.message)
            }
        } catch (e: Exception){
            Log.d("TAG", "signUp: $e.message")
            AuthResult.UnknownError(e.message)
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