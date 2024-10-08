package com.example.knowledgeshelf.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.knowledgeshelf.data.model.auth.login.LoginRequest
import com.example.knowledgeshelf.data.model.auth.login.LoginResponse
import com.example.knowledgeshelf.data.model.auth.register.RegistrationRequest
import com.example.knowledgeshelf.data.model.auth.register.RegistrationResponse
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.data.repository.UserRepository
import com.example.knowledgeshelf.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor (private val userRepository: UserRepository):ViewModel() {


    private val _registrationResult = MutableStateFlow<Resource<RegistrationResponse>?>(null)
    val registrationResult: StateFlow<Resource<RegistrationResponse>?>
        get() = _registrationResult

    private val _loginResult = MutableStateFlow<Resource<LoginResponse>?>(null)
    val loginResult: StateFlow<Resource<LoginResponse>?>
        get() = _loginResult

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated

    private val _userProfile = MutableStateFlow<UserRepository.UserProfile?>(null)
    val userProfile: StateFlow<UserRepository.UserProfile?> get() = _userProfile



    fun registerUser(registrationRequest: RegistrationRequest) {
        viewModelScope.launch {
            _registrationResult.value = Resource.Loading
            val result = userRepository.registerUser(registrationRequest)
            _registrationResult.value = result
        }
    }

    fun loginUser(loginRequest: LoginRequest, context: Context) {
        viewModelScope.launch {
            _loginResult.value = Resource.Loading
            val result = userRepository.loginUser(loginRequest, context)
            _loginResult.value = result
        }
    }

    fun checkUserAuthentication(context: Context) {
        viewModelScope.launch {
            val isAuthenticated = userRepository.checkTokensAndLogin(context)
            _isUserAuthenticated.value = isAuthenticated
        }
    }
    fun loadUserProfile(context: Context) {
        viewModelScope.launch {
            val profile = userRepository.getUserProfile(context)
            _userProfile.value = profile
        }
    }
    fun logoutUser(context: Context) {
        viewModelScope.launch {
            userRepository.logoutUser(context)
        }
    }

}