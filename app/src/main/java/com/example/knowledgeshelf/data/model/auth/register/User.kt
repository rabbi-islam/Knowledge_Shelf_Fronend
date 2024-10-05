package com.example.knowledgeshelf.data.model.auth.register

data class User(
    val fullName: String,
    val email: String,
    val role: String,
    val _id: String,
    val avatar: String,
    val createdAt: String,
    val updatedAt: String
)
