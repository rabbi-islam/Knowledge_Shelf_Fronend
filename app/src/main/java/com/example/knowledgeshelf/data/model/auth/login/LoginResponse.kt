package com.example.knowledgeshelf.data.model.auth.login

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val success: Boolean,
    val user: LoginUser
)