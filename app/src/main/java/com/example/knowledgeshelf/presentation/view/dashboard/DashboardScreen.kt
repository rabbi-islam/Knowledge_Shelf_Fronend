import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.knowledgeshelf.data.model.BottomNavItem
import com.example.knowledgeshelf.navigation.Screens
import com.example.knowledgeshelf.presentation.view.dashboard.BottomNavGraph

@Composable
fun DashboardScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        BottomNavGraph(navController = navController, modifier = Modifier.padding(it), onLogout = {onLogout()})
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = Color.White
    ) {
        val currentDestination by navController.currentBackStackEntryAsState()
        val currentRoute = currentDestination?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    if (currentRoute == item.route) {
                        Icon(imageVector = item.selectedIcon, contentDescription = item.title)
                    } else {
                        Icon(imageVector = item.unSelectedIcon, contentDescription = item.title)
                    }
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}



