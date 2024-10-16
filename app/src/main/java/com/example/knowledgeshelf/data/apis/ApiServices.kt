package com.example.knowledgeshelf.data.apis

import com.example.knowledgeshelf.data.model.auth.login.LoginRequest
import com.example.knowledgeshelf.data.model.auth.login.LoginResponse
import com.example.knowledgeshelf.data.model.auth.login.Tokens
import com.example.knowledgeshelf.data.model.auth.register.RegistrationRequest
import com.example.knowledgeshelf.data.model.auth.register.RegistrationResponse
import com.example.knowledgeshelf.data.model.book.AddBookResponse
import com.example.knowledgeshelf.data.model.book.Books
import com.example.knowledgeshelf.data.model.book.DeleteBookResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ApiServices {
    @POST("auth/registration")
    suspend fun registerUser(
        @Body registrationRequest: RegistrationRequest
    ): Response<RegistrationResponse>

    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/refresh-token")
    suspend fun refreshToken(token:String): Response<Tokens>

    //book
    @GET("book")
    suspend fun getBooks(): Response<Books>

    @DELETE("book/{id}")
    suspend fun deleteBook(@Path("id") bookId: String): Response<DeleteBookResponse>

    @Multipart
    @POST("book")
    suspend fun addBook(
        @PartMap bookData: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part?
    ): Response<AddBookResponse>

}