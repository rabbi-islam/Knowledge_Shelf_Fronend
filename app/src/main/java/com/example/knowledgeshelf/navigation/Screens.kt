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

    @Serializable
    data object AddBookScreen : Screens

    @Serializable
    data class CheckoutScreen(val name:String, val price:String, val authorName:String, val image:String) : Screens

    @Serializable
    data object HomeNavScreen : Screens

    @Serializable
    data object CartNavScreen : Screens

    @Serializable
    data object ProfileNavScreen : Screens

}