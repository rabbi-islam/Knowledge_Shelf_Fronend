package com.example.knowledgeshelf.data.repository

import android.content.Context
import android.util.Log
import com.auth0.android.jwt.JWT
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

        return if (accessToken != null) {
            true
        } else if (refreshToken != null) {
            try {
                val response = apiService.refreshToken(refreshToken)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        tokenManager.saveTokens(responseBody.accessToken, responseBody.refreshToken)
                        true
                    } else {
                        false
                    }
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }

    private fun decodeJWT(token: String): UserProfile? {
        return try {
            val jwt = JWT(token)
            val name = jwt.getClaim("fullName").asString() // Replace with your actual claims
            val email = jwt.getClaim("email").asString()

            UserProfile(fullName = name, email = email) // Create a UserProfile data class to hold this information
        } catch (e: Exception) {
            null
        }
    }

    fun getUserProfile(context: Context): UserProfile? {
        val tokenManager = TokenManager(context)
        val accessToken = tokenManager.getAccessToken()
        return accessToken?.let { decodeJWT(it) }
    }

    fun logoutUser(context: Context) {
        val tokenManager = TokenManager(context)
        tokenManager.clearTokens()
    }

    data class UserProfile(
        val fullName: String?,
        val email: String?
    )

}