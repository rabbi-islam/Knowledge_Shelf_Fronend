package com.example.knowledgeshelf.data.model.auth.register

data class RegistrationResponse(
    val statusCode:Int,
    val success: Boolean,
    val message: String,
    val user: User
)
