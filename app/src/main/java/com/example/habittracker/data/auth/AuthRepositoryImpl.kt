package com.example.habittracker.data.auth

import android.content.SharedPreferences
import com.google.android.gms.auth.api.Auth
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sharedPrefs: SharedPreferences
): AuthRepository {
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
            if (e.code() == 401){
                AuthResult.Unauthorized()
            } else{
                AuthResult.UnknownError()
            }
        } catch (e: Exception){
            AuthResult.UnknownError()
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
            val token = sharedPrefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate(token)

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
}