package com.example.knowledgeshelf.presentation.view.login
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.knowledgeshelf.data.model.auth.login.LoginRequest
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.presentation.ui.theme.KnowledgeShelfTheme
import com.example.knowledgeshelf.presentation.view.components.HeaderText
import com.example.knowledgeshelf.presentation.view.components.LoginTextField
import com.example.knowledgeshelf.presentation.viewmodel.UserViewmodel

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(
    viewModel: UserViewmodel = hiltViewModel(),
    onLoginSuccess:()-> Unit,
    onSignUpTextClick: () -> Unit
) {

    val (email, onEmailChange) = rememberSaveable { mutableStateOf("") }
    val (password, onPasswordChange) = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    // Collect state from ViewModel
    val loginState by viewModel.loginResult.collectAsState()
    val isLoading = loginState is Resource.Loading
    val isSuccess = loginState is Resource.Success
    val isError = loginState is Resource.Error

    if (isSuccess) {
        LaunchedEffect(Unit) {
            onLoginSuccess()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding)
            .verticalScroll(rememberScrollState()), // For scrolling in case of overflow
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))  // This pushes the form to the middle vertically

        // Header text at the top
        HeaderText(
            text = "Sign In",
            modifier = Modifier
                .padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start)
        )

        Spacer(Modifier.height(defaultPadding))

        // Email Field
        LoginTextField(
            value = email,
            onValueChange = onEmailChange,
            labelText = "Email",
            leadingIcon = Icons.Rounded.Email,
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = Color.Red,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        Spacer(Modifier.height(defaultPadding))

        // Password Field
        LoginTextField(
            value = password,
            onValueChange = onPasswordChange,
            labelText = "Password",
            leadingIcon = Icons.Rounded.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password
        )
        if (passwordError.isNotEmpty()) {
            Text(
                text = passwordError,
                color = Color.Red,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        Spacer(Modifier.height(defaultPadding))

        if (isLoading) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
        }

        // Sign Up Button
        Button(
            onClick = {
                passwordError = ""
                emailError = ""
                if (email.isEmpty()){
                    emailError = "Email Required!"
                }
                if (password.isEmpty()){
                    passwordError = "Password Required!"
                }
                // Trigger login process if inputs are valid
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.loginUser(LoginRequest(email, password), context) // Call ViewModel's login function
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Sign In")
        }

        Spacer(Modifier.height(defaultPadding))

        // Error message display
        if (isError) {
            val errorMessage = (loginState as Resource.Error).message
            Text(
                text = errorMessage ?: "An error occurred",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Clickable text for sign up
        val signUpText = "Sign Up"
        val signUpAnnotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append("Don't have an account?")
            }
            append("  ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                pushStringAnnotation(signUpText, signUpText)
                append(signUpText)
            }
        }

        ClickableText(
            text = signUpAnnotatedString,
        ) { offset ->
            signUpAnnotatedString.getStringAnnotations(offset, offset).forEach {
                if (it.tag == signUpText) {
                    onSignUpTextClick()
                }
            }
        }

        Spacer(Modifier.weight(1f))  // This pushes the form content upwards from the bottom
    }
}


