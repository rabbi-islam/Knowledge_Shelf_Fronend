package com.example.knowledgeshelf.data.model.auth.register

data class RegistrationRequest(
    val fullName: String,
    val email: String,
    val password: String
)
