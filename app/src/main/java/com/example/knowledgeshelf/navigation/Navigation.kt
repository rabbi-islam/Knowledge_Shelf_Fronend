package com.example.knowledgeshelf.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.knowledgeshelf.presentation.view.login.LoginScreen
import com.example.knowledgeshelf.presentation.view.profile.ProfileScreen
import com.example.knowledgeshelf.presentation.view.registration.SignUpScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen
    ) {
        composable<Screens.RegistrationScreen> {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Screens.LoginScreen)
                },
                onSignInTextClick = {
                    navController.navigate(Screens.LoginScreen)
                },
                onPolicyClick = {},
                onPrivacyClick = {}
            )
        }

        composable<Screens.LoginScreen> {
            LoginScreen(
                onSignUpTextClick = {
                    navController.navigate(Screens.RegistrationScreen)
                },
                onLoginSuccess = {
                    navController.navigate(Screens.ProfileScreen)
                }
            )
        }

        composable<Screens.ProfileScreen> {
          ProfileScreen()
        }
    }
}