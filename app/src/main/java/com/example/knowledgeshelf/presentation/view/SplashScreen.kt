package com.example.knowledgeshelf.presentation.view

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.knowledgeshelf.R
import com.example.knowledgeshelf.presentation.viewmodel.UserViewmodel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow


@Composable
fun AnimatedSplashScreen(
    onSplashFinished: () -> Unit, // Keep it as a simple callback
    viewModel: UserViewmodel
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000),
        label = ""
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(1500)
        onSplashFinished()
    }

    SplashScreen(alphaAnim.value)
}















@Composable
fun SplashScreen(alpha:Float) {
    Box(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.alpha(alpha = alpha),
            painter = painterResource(id = R.drawable.book),
            contentDescription = "book")
    }
}

