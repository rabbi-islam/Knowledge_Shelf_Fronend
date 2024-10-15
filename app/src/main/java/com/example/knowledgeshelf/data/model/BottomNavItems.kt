package com.example.knowledgeshelf.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.knowledgeshelf.navigation.Screens


sealed class BottomNavItem(val title: String, val selectedIcon: ImageVector, val unSelectedIcon: ImageVector,val screens: Screens) {
    data object Home : BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home,Screens.HomeNavScreen)
    data object Cart : BottomNavItem("Cart", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart, Screens.CartNavScreen)
    data object Profile : BottomNavItem("Profile", Icons.Filled.Person, Icons.Outlined.Person,Screens.ProfileNavScreen)
}

