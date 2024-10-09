package com.example.knowledgeshelf.data.repository

import android.content.Context
import com.example.knowledgeshelf.data.apis.ApiServices
import com.example.knowledgeshelf.data.model.UserProfile
import com.example.knowledgeshelf.data.model.book.Books
import com.example.knowledgeshelf.data.model.book.DeleteBookResponse
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.utils.JwtToken
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
}



