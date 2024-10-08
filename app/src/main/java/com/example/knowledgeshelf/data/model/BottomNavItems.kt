package com.example.knowledgeshelf.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(val route: String,val title: String, val selectedIcon: ImageVector, val unSelectedIcon: ImageVector) {
    data object Home : BottomNavItem("home","Home", Icons.Filled.Home, Icons.Outlined.Home)
    data object Cart : BottomNavItem("cart","Cart", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart)
    data object Profile : BottomNavItem("profile","Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

