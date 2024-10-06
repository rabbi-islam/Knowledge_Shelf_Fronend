package com.example.knowledgeshelf.data.apis

import com.example.knowledgeshelf.data.model.auth.login.LoginRequest
import com.example.knowledgeshelf.data.model.auth.login.LoginResponse
import com.example.knowledgeshelf.data.model.auth.login.Tokens
import com.example.knowledgeshelf.data.model.auth.register.RegistrationRequest
import com.example.knowledgeshelf.data.model.auth.register.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("auth/registration")
    suspend fun registerUser(
        @Body registrationRequest: RegistrationRequest
    ): Response<RegistrationResponse>

    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/refresh-token")
    suspend fun refreshToken(token:String): Response<Tokens>
}