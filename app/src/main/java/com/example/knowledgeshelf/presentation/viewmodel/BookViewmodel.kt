package com.example.knowledgeshelf.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowledgeshelf.data.model.UserProfile
import com.example.knowledgeshelf.data.model.auth.register.RegistrationResponse
import com.example.knowledgeshelf.data.model.book.Books
import com.example.knowledgeshelf.data.model.book.DeleteBookResponse
import com.example.knowledgeshelf.data.repository.BookRepository
import com.example.knowledgeshelf.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewmodel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {
    private val _bookResult = MutableStateFlow<Resource<Books>?>(null)
    val bookResult: StateFlow<Resource<Books>?>
        get() = _bookResult

    private val _deleteBookResult = MutableStateFlow<Resource<DeleteBookResponse>?>(null)
    val deleteBookResult: StateFlow<Resource<DeleteBookResponse>?>
        get() = _deleteBookResult

    private val _userInfoFromToken = MutableStateFlow<UserProfile?>(null)
    val userInfoFromToken: StateFlow<UserProfile?> get() = _userInfoFromToken


    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            _bookResult.value = Resource.Loading
            val result = bookRepository.getBooks()
            _bookResult.value = result
        }
    }

    fun getUserInfoFromJwtToken(context: Context) {
        viewModelScope.launch {
            val profile = bookRepository.getUserProfile(context)
            _userInfoFromToken.value = profile
        }
    }

    fun deleteBook(bookId: String) {
        viewModelScope.launch {
            _deleteBookResult.value = Resource.Loading
            val result = bookRepository.deleteBook(bookId)
            _deleteBookResult.value = result

            if (result is Resource.Success) {
                _deleteBookResult.value = result
                delay(2000)
                _deleteBookResult.value = null
            } else if (result is Resource.Error) {
                _deleteBookResult.value = result
                delay(2000)
                _deleteBookResult.value = null
            }
            // Reload books after deletion to reflect the changes
            getBooks()
        }
    }




}