package com.example.knowledgeshelf.data.model.book

data class AddBookResponse(
    val statusCode:Int,
    val book: BookX,
    val success: Boolean,
    val message: String?=null
)