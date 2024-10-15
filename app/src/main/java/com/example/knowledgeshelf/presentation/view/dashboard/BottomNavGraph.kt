package com.example.knowledgeshelf.presentation.view.dashboard

import ProfileScreen
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.knowledgeshelf.data.model.BottomNavItem
import com.example.knowledgeshelf.navigation.Screens
import com.example.knowledgeshelf.presentation.view.CartScreen
import com.example.knowledgeshelf.presentation.view.HomeScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


//@Composable
//fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier, onLogout: () -> Unit) {
//    NavHost(
//        navController = navController,
//        startDestination = BottomNavItem.Home.route,
//        modifier = modifier
//    ) {
//        composable(BottomNavItem.Home.route) {
//            HomeScreen(navController = navController)
//        }
//        composable(BottomNavItem.Cart.route) {
//            // This is for when the Cart is accessed from the bottom nav
//            CartScreen(
//                bookId = "",
//                bookName = "",
//                authorName = "",
//                price = "",
//                imageUrl = ""
//            )
//        }
//        composable(
//            route = "cart/{bookId}/{bookName}/{authorName}/{price}/{imageUrl}",
//            arguments = listOf(
//                navArgument("bookId") { type = NavType.StringType },
//                navArgument("bookName") { type = NavType.StringType },
//                navArgument("authorName") { type = NavType.StringType },
//                navArgument("price") { type = NavType.StringType },
//                navArgument("imageUrl") { type = NavType.StringType }
//            )
//        ) { backStackEntry ->
//            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
//            val bookName = URLDecoder.decode(backStackEntry.arguments?.getString("bookName") ?: "", StandardCharsets.UTF_8.toString())
//            val authorName = URLDecoder.decode(backStackEntry.arguments?.getString("authorName") ?: "", StandardCharsets.UTF_8.toString())
//            val price = backStackEntry.arguments?.getString("price") ?: ""
//            val imageUrl = URLDecoder.decode(backStackEntry.arguments?.getString("imageUrl") ?: "", StandardCharsets.UTF_8.toString())
//
//            CartScreen(
//                bookId = bookId,
//                bookName = bookName,
//                authorName = authorName,
//                price = price,
//                imageUrl = imageUrl
//            )
//        }
//        composable(BottomNavItem.Profile.route) {
//            ProfileScreen(
//                onBackClick = { navController.navigate(BottomNavItem.Home.route) },
//                onSuccessLogout = {
//                    onLogout()
//                }
//            )
//        }
//    }
//}





