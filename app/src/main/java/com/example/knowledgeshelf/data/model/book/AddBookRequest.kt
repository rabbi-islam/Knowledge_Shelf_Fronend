package com.example.knowledgeshelf.data.model.book

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

data class BookRequest(
    val name: String,
    val price: Double,
    val authorName: String,
    val stock: Int,
    val description: String,
    val image: MultipartBody.Part,
    val publishedDate: String
) {
    private fun String.toRequestBody(): RequestBody {
        return RequestBody.create("text/plain".toMediaType(), this)
    }

    fun toRequestBodyMap(): Map<String, RequestBody> {
        return mapOf(
            "name" to name.toRequestBody(),
            "price" to price.toString().toRequestBody(),
            "authorName" to authorName.toRequestBody(),
            "stock" to stock.toString().toRequestBody(),
            "description" to description.toRequestBody(),
            "publishedDate" to publishedDate.toRequestBody()
        )
    }
}


