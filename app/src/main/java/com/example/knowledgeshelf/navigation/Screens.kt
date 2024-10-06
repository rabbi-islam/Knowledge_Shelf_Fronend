package com.example.knowledgeshelf.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object SplashScreen : Screens

    @Serializable
    data object RegistrationScreen : Screens

    @Serializable
    data object LoginScreen : Screens

    @Serializable
    data object ProfileScreen : Screens
}