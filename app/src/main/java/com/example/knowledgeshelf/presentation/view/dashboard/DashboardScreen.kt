import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.knowledgeshelf.data.model.BottomNavItem
import com.example.knowledgeshelf.presentation.viewmodel.BookViewmodel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    onAddButtonClick: () -> Unit,
    viewmodel:BookViewmodel = hiltViewModel(),
    content: @Composable () -> Unit,
    navController: NavController
) {

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
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = Color.White
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (index == selectedItem) item.selectedIcon else item.unSelectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.screens) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White ,
                    unselectedIconColor = Color.Gray, // Icon color when not selected
                    selectedTextColor = Color.Black, // Text color when selected
                    unselectedTextColor = Color.Gray, // Text color when not selected
                    indicatorColor = Color.Black // Background color of the selected item
                )
            )
        }
    }
}




