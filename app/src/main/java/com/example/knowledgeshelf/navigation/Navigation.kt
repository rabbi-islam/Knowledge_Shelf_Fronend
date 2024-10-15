package com.example.knowledgeshelf.navigation

import DashboardScreen
import ProfileScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.knowledgeshelf.presentation.view.addBook.AddBookScreen
import com.example.knowledgeshelf.presentation.view.AnimatedSplashScreen
import com.example.knowledgeshelf.presentation.view.CartScreen
import com.example.knowledgeshelf.presentation.view.HomeScreen
import com.example.knowledgeshelf.presentation.view.login.LoginScreen
import com.example.knowledgeshelf.presentation.view.registration.SignUpScreen
import com.example.knowledgeshelf.presentation.viewmodel.BookViewmodel
import com.example.knowledgeshelf.presentation.viewmodel.UserViewmodel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(modifier: Modifier = Modifier, userViewModel: UserViewmodel, bookViewmodel: BookViewmodel) {

    val navController = rememberNavController()
    val isAuthenticated by userViewModel.isUserAuthenticated.collectAsState()
    var isSplashFinished by remember { mutableStateOf(false) }

    // Show splash screen for a certain duration before checking authentication
    LaunchedEffect(Unit) {
        delay(1500) // Wait for 3 seconds
        isSplashFinished = true // Set splash screen as finished
    }

    // Handle navigation based on authentication state after the splash is finished
    LaunchedEffect(isSplashFinished) {
        if (isSplashFinished) {
            if (isAuthenticated) {
                navController.navigate(Screens.DashboardScreen) {
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
        startDestination = Screens.SplashScreen
    ) {

        composable<Screens.SplashScreen> {
            AnimatedSplashScreen(
                onSplashFinished = {},
                viewModel = userViewModel
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
                    navController.navigate(Screens.DashboardScreen){
                        popUpTo(0)
                    }
                }
            )
        }
        composable<Screens.DashboardScreen> {
            BottomNavigation(
                userViewModel,
                bookViewmodel,
                onSuccessfulLogout = {
                    navController.navigate(Screens.LoginScreen) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable<Screens.AddBookScreen> {
            AddBookScreen()
        }

        composable<Screens.CheckoutScreen> {

        }

    }
}

@Composable
fun BottomNavigation(
    userViewModel: UserViewmodel,
    bookViewModel: BookViewmodel,
    onSuccessfulLogout: () -> Unit
) {
    val navController = rememberNavController()

    DashboardScreen(
        onAddButtonClick = {},
        viewmodel = bookViewModel,
        content = {
            NavHost(
                navController = navController,
                startDestination = Screens.HomeNavScreen
            ) {
                composable<Screens.HomeNavScreen> {
                    HomeScreen(viewmodel = bookViewModel)
                }

                composable<Screens.CartNavScreen> {
                    // CartScreen content
                }

                composable<Screens.ProfileNavScreen> {
                    ProfileScreen(
                        viewModel = userViewModel,
                        onBackClick = { navController.navigate(Screens.HomeNavScreen) },
                        onSuccessLogout = {onSuccessfulLogout ()}
                    )
                }
            }
        },
        navController = navController
    )
}
































