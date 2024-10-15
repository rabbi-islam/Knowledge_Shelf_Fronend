package com.example.knowledgeshelf

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.knowledgeshelf.navigation.Navigation
import com.example.knowledgeshelf.presentation.ui.theme.KnowledgeShelfTheme
import com.example.knowledgeshelf.presentation.view.registration.SignUpScreen
import com.example.knowledgeshelf.presentation.viewmodel.BookViewmodel
import com.example.knowledgeshelf.presentation.viewmodel.UserViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: UserViewmodel by viewModels()
    private val bookViewModel: BookViewmodel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            //hiding status bar
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
            KnowledgeShelfTheme {
                val context = LocalContext.current // Using 'this' to get the Activity context

                LaunchedEffect(Unit) {
                    viewModel.checkUserAuthentication(context) // Call authentication check here
                }

                // Use Scaffold to manage layout
                Scaffold(
                    //containerColor = Color(0xFF1b2a48), // Set background color for the Scaffold
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Navigation(userViewModel = viewModel, bookViewmodel = bookViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
