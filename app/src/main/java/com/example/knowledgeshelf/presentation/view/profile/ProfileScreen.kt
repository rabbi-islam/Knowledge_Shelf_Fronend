import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.knowledgeshelf.R
import com.example.knowledgeshelf.navigation.Screens
import com.example.knowledgeshelf.presentation.viewmodel.UserViewmodel
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    viewModel: UserViewmodel = hiltViewModel(),
    onSuccessLogout: () -> Unit,
) {


    val userProfile by viewModel.userProfile.collectAsState()

    val context = LocalContext.current
    // Load user profile when the screen is launched
    LaunchedEffect(Unit) {
        viewModel.loadUserProfile(context)
    }

    val email by remember { mutableStateOf("johndoe@example.com") }

    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.logoutUser(context)
                        onSuccessLogout()

                    }) {
                        Icon(
                            Icons.Outlined.Logout,
                            tint = Color.White,
                            contentDescription = "Logout"
                        )
                    }
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1b2a48) // Make top bar match background color
                ),

                )
        }
    ) { innerPadding ->
        // Background section that spans the top and content until the button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 70.dp, bottomEnd = 70.dp))
                    .background(Color(0xFF1b2a48))
                    .padding(26.dp)
                    .height(IntrinsicSize.Min) // Limit the background height to the content size

            ) {
                // Profile content inside the green background
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Box(contentAlignment = Alignment.BottomEnd) {
                        userProfile?.let {
                            val avatarUrl = remember { it.avatar.toString() }
                            AsyncImage(
                                model = avatarUrl,
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                        IconButton(
                            onClick = { /* TODO: Implement image update logic */ },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(4.dp)
                        ) {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = "Edit Profile Picture",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    userProfile?.let {
                        Text(
                            text = it.fullName!!,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = it.email!!, fontSize = 18.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(

                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Yellow // Green button to match background
                        ),
                        shape = RoundedCornerShape(percent = 50) // Rounded button
                    ) {
                        Text(text = "Edit Info", fontSize = 18.sp, color = Color.Black)
                    }
                }
            }


        }
    }
}

