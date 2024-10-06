package com.example.knowledgeshelf.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.knowledgeshelf.presentation.view.AnimatedSplashScreen
import com.example.knowledgeshelf.presentation.view.SplashScreen
import com.example.knowledgeshelf.presentation.view.login.LoginScreen
import com.example.knowledgeshelf.presentation.view.profile.ProfileScreen
import com.example.knowledgeshelf.presentation.view.registration.SignUpScreen
import com.example.knowledgeshelf.presentation.viewmodel.UserViewmodel
import kotlinx.coroutines.delay

@Composable
fun Navigation(modifier: Modifier = Modifier, viewModel: UserViewmodel) {

    val navController = rememberNavController()
    val isAuthenticated by viewModel.isUserAuthenticated.collectAsState()
    var isSplashFinished by remember { mutableStateOf(false) }

    // Show splash screen for a certain duration before checking authentication
    LaunchedEffect(Unit) {
        delay(2500) // Wait for 3 seconds
        isSplashFinished = true // Set splash screen as finished
    }

    // Handle navigation based on authentication state after the splash is finished
    LaunchedEffect(isSplashFinished) {
        if (isSplashFinished) {
            if (isAuthenticated) {
                navController.navigate(Screens.ProfileScreen) {
                    popUpTo(0) // Clears the backstack
                }
            } else {
                navController.navigate(Screens.LoginScreen) {
                    popUpTo(0) // Clears the backstack
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen   //if (isAuthenticated) Screens.ProfileScreen else Screens.LoginScreen
    ) {

        composable<Screens.SplashScreen> {
            AnimatedSplashScreen(
                onSplashFinished = {},
                viewModel = viewModel
            )
        }

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