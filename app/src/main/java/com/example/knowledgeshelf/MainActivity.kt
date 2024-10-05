package com.example.knowledgeshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.knowledgeshelf.navigation.Navigation
import com.example.knowledgeshelf.presentation.ui.theme.KnowledgeShelfTheme
import com.example.knowledgeshelf.presentation.view.registration.SignUpScreen
import com.example.knowledgeshelf.presentation.viewmodel.UserViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private val viewModel: UserViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KnowledgeShelfTheme {

                Navigation()

            }
        }
    }
}
