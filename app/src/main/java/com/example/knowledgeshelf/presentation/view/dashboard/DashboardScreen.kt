import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.knowledgeshelf.data.model.BottomNavItem
import com.example.knowledgeshelf.navigation.Screens
import com.example.knowledgeshelf.presentation.view.AddBookScreen
import com.example.knowledgeshelf.presentation.view.dashboard.BottomNavGraph
import com.example.knowledgeshelf.presentation.viewmodel.BookViewmodel

@Composable
fun DashboardScreen(
    onLogout: () -> Unit,
    onAddButtonClick: () -> Unit,
    viewmodel:BookViewmodel = hiltViewModel()
) {
    val navController = rememberNavController()
    val userInfoFromToken by viewmodel.userInfoFromToken.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewmodel.getUserInfoFromJwtToken(context)
    }

    Scaffold(
        floatingActionButton = {
            if (userInfoFromToken?.role == "admin") { // Check if the role is 'admin'
                FloatingActionButton(onClick = { onAddButtonClick() }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
                }
            }
        },
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



