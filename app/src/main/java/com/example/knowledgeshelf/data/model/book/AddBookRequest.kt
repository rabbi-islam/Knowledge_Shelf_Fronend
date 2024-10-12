package com.example.knowledgeshelf.data.model.book

import okhttp3.MultipartBody

data class AddBookRequest(
    val name: String,
    val price: Double,
    val authorName: String,
    val stock: Int,
    val description: String,
    val image: MultipartBody.Part,
    val publishedDate: String
)

