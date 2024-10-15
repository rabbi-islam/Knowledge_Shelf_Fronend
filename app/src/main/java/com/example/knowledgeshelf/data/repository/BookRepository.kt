package com.example.knowledgeshelf.data.repository

import android.content.Context
import android.util.Log
import com.example.knowledgeshelf.data.apis.ApiServices
import com.example.knowledgeshelf.data.model.UserProfile
import com.example.knowledgeshelf.data.model.book.AddBookResponse
import com.example.knowledgeshelf.data.model.book.BookRequest
import com.example.knowledgeshelf.data.model.book.Books
import com.example.knowledgeshelf.data.model.book.DeleteBookResponse
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.utils.JwtToken
import com.example.knowledgeshelf.utils.Utils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.PartMap
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

    suspend fun addBook(bookRequest: BookRequest, image: MultipartBody.Part): Resource<AddBookResponse> {
        return try {

            val bookData = bookRequest.toRequestBodyMap()

            val response = apiService.addBook(bookData, image)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    return Resource.Success(message = "Book added", data = body)
                } else {
                    val errorMessage = body?.message ?: "Unknown error occurred"
                    return Resource.Error(errorMessage)
                }
            } else {
                return Resource.Error(
                    "Error adding book: ${response.errorBody()?.string() ?: "Unknown error"}"
                )
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

}



