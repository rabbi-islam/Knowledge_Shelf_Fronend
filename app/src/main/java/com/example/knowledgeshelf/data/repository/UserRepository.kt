package com.example.knowledgeshelf.data.repository

import android.content.Context
import android.util.Log
import com.example.knowledgeshelf.data.model.auth.register.RegistrationRequest
import com.example.knowledgeshelf.data.model.auth.register.RegistrationResponse
import com.example.knowledgeshelf.data.apis.ApiServices
import com.example.knowledgeshelf.data.model.auth.login.LoginRequest
import com.example.knowledgeshelf.data.model.auth.login.LoginResponse
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.utils.TokenManager
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiServices) {

    // Function to register a user
    suspend fun registerUser(registrationRequest: RegistrationRequest): Resource<RegistrationResponse> {
        return try {
            val response = apiService.registerUser(registrationRequest)
            if (response.isSuccessful){
                if (response.body()!!.statusCode == 409){
                    Resource.Error(response.body()!!.message)
                }else{
                    Resource.Success(response.body()!!.message, response.body()!!)
                }
            }else{
                Resource.Error(response.message())
            }
        }catch (e:Exception){
                Resource.Error(e.localizedMessage ?: "An Error Occurred")
        }
    }

    suspend fun loginUser(loginRequest: LoginRequest, context:Context): Resource<LoginResponse>{
        return try {
            val response = apiService.loginUser(loginRequest)
            val responseBody = response.body()
            if (responseBody!!.success){
                //save tokens
                val tokenManager = TokenManager(context)
                tokenManager.saveTokens(responseBody.accessToken, responseBody.refreshToken)
                Resource.Success("Logged In.",responseBody)
            }else
                Resource.Error("Wrong Email or Password.")
        }catch (e:Exception){
            Resource.Error(e.localizedMessage ?: "An Error Occurred")
        }
    }

    // checking token
    suspend fun checkTokensAndLogin(context: Context): Boolean {
        val tokenManager = TokenManager(context)
        val accessToken = tokenManager.getAccessToken()
        val refreshToken = tokenManager.getRefreshToken()

        Log.d("UserRepository", "AccessToken: $accessToken, RefreshToken: $refreshToken")

        return if (accessToken != null) {
            Log.d("UserRepository", "User is authenticated with access token.")
            true
        } else if (refreshToken != null) {
            try {
                val response = apiService.refreshToken(refreshToken)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        tokenManager.saveTokens(responseBody.accessToken, responseBody.refreshToken)
                        Log.d("UserRepository", "Tokens refreshed successfully.")
                        true
                    } else {
                        Log.d("UserRepository", "Failed to refresh tokens.")
                        false
                    }
                } else {
                    Log.d("UserRepository", "Refresh token response unsuccessful.")
                    false
                }
            } catch (e: Exception) {
                Log.e("UserRepository", "Error refreshing token: ${e.localizedMessage}")
                false
            }
        } else {
            Log.d("UserRepository", "No valid tokens available.")
            false
        }
    }


}