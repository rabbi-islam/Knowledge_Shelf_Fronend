package com.example.knowledgeshelf.domain

sealed class Resource<out  T> {
    data class Success<out T>(val message:String? = null,val data:T):Resource<T>()
    data class Error<out T>(val message:String, val cause:Exception? = null):Resource<T>()
    data object Loading:Resource<Nothing>()
}