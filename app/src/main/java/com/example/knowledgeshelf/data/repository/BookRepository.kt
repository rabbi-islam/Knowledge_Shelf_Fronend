package com.example.knowledgeshelf.data.repository

import android.content.Context
import android.util.Log
import com.example.knowledgeshelf.data.apis.ApiServices
import com.example.knowledgeshelf.data.model.UserProfile
import com.example.knowledgeshelf.data.model.book.AddBookRequest
import com.example.knowledgeshelf.data.model.book.AddBookResponse
import com.example.knowledgeshelf.data.model.book.Books
import com.example.knowledgeshelf.data.model.book.DeleteBookResponse
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.utils.JwtToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class BookRepository @Inject constructor(private val apiService: ApiServices) {

    suspend fun getBooks(): Resource<Books> {
        return try {
            val books = apiService.getBooks()

            if (books.isSuccessful && books.body() != null) {
                Resource.Success(data = books.body()!!)
            } else {
                Resource.Error(books.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An Error Occurred")
        }
    }

    fun getUserProfile(context: Context): UserProfile? {
        return JwtToken.getUserProfileFromToken(context)
    }

    suspend fun deleteBook(bookId: String): Resource<DeleteBookResponse> {
        return try {
            val response = apiService.deleteBook(bookId)
            val result = response.body()


            if (response.isSuccessful && result != null && result.success) {
                Resource.Success(message = result.message)
            } else {
                Resource.Error(message = "Book deletion failed!")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An Error Occurred")
        }
    }

    suspend fun addBook(
        name: String,
        price: Double,
        authorName: String,
        stock: Int,
        description: String,
        image: MultipartBody.Part, // File upload part
        publishedDate: String
    ): Resource<AddBookResponse> {
        return try {

            val nameRequest = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val priceRequest = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val authorNameRequest = authorName.toRequestBody("text/plain".toMediaTypeOrNull())
            val stockRequest = stock.toString().toRequestBody("application/json".toMediaTypeOrNull())
            val descriptionRequest = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val publishedDateRequest = publishedDate.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = apiService.addBook(
                name = nameRequest,
                price = priceRequest,
                authorName = authorNameRequest,
                stock = stockRequest,
                description = descriptionRequest,
                image = image,
                publishedDate = publishedDateRequest
            )

            val responseCode = response.code()
            Log.d("responseCode", responseCode.toString())
            Log.d("responseCode", response.body().toString())
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Log.d("BookRepository", "Book added successfully: $body")
                    return Resource.Success(message = "Book added", data = body)
                } else {
                    val errorMessage = body?.message ?: "Unknown error occurred"
                    Log.e("BookRepository", "Error: $errorMessage")
                    return Resource.Error(errorMessage)
                }
            } else {
                Log.e("BookRepository", "Error: ${response.errorBody()?.string() ?: "Unknown error"}")
                return Resource.Error("Error adding book: ${response.errorBody()?.string() ?: "Unknown error"}")
            }
        } catch (e: Exception) {
            Log.e("BookRepository", "Exception: ${e.localizedMessage}")
            Resource.Error(e.localizedMessage ?: "Error Occurred")
        }
    }

    // Function to add a new book
//    suspend fun addBook(): Resource<AddBookResponse> { // Replace YourResponseType with the actual response type
//        return try {
//            val response = apiService.addBook() // Call the API with the data class
//            val responseCode = response.code()
//            Log.d("responseCode", responseCode.toString())
//
//            if (response.body()!!.success) {
//
//                Resource.Success(message = "Book added", data = response.body()!!)
//            } else {
//                Resource.Error(response.message())
//            }
//        } catch (e: Exception) {
//            Resource.Error(e.localizedMessage ?: "An Error Occurred")
//        }
//    }
}



