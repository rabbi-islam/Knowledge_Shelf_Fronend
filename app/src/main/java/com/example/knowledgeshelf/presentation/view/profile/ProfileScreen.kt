package com.example.knowledgeshelf.presentation.view.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.knowledgeshelf.utils.TokenManager

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    Box (modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = TokenManager(LocalContext.current).getAccessToken().toString(), fontSize = 18.sp)
    }
}