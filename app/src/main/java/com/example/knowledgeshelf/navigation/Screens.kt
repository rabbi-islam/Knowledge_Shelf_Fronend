package com.example.knowledgeshelf.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screens {

    @Serializable
    data object SplashScreen : Screens

    @Serializable
    data object RegistrationScreen : Screens

    @Serializable
    data object LoginScreen : Screens

    @Serializable
    data object DashboardScreen : Screens

//    @Serializable
//    data object HomeScreen : Screens
//
//    @Serializable
//    data object CartScreen : Screens
//
//    @Serializable
//    data object ProfileScreen : Screens

}