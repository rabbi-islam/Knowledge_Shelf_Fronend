package com.example.knowledgeshelf.data.model.book

data class BookX(
    val _id: String,
    val addedBy: String,
    val authorName: String,
    val description: String,
    val image: String,
    val imagekey: String,
    val name: String,
    val price: Double,
    val publishedDate: String,
    val stock: Int
)