package com.example.knowledgeshelf.presentation.view.dashboard

import ProfileScreen
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.knowledgeshelf.data.model.BottomNavItem
import com.example.knowledgeshelf.navigation.Screens
import com.example.knowledgeshelf.presentation.view.HomeScreen


@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier, onLogout: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Cart.route) {
            CartScreen() // Define CartScreen content
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.navigate(BottomNavItem.Home.route) },
                onSuccessLogout = {
                        onLogout()

//                    navController.navigate(Screens.LoginScreen.toString()) {
//                        popUpTo(Screens.DashboardScreen.toString()) { inclusive = true }
//                    }
                }
            ) // Define ProfileScreen content
        }
    }
}




@Composable
fun CartScreen() {
    Text(text = "Cart Screen")
}


