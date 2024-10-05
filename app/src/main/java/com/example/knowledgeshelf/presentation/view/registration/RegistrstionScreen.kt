package com.example.knowledgeshelf.presentation.view.registration

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.knowledgeshelf.data.model.auth.register.RegistrationRequest
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.presentation.view.components.HeaderText
import com.example.knowledgeshelf.presentation.view.components.LoginTextField
import com.example.knowledgeshelf.presentation.viewmodel.UserViewmodel

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun SignUpScreen(
    viewModel: UserViewmodel = hiltViewModel(),
    onSignUpSuccess: () -> Unit,  // Callback when registration is successful
    onSignInTextClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    // Form state
    val (fullName, onFirstNameChange) = rememberSaveable { mutableStateOf("") }
    val (email, onEmailChange) = rememberSaveable { mutableStateOf("") }
    val (password, onPasswordChange) = rememberSaveable { mutableStateOf("") }
    val (agree, onAgreeChange) = rememberSaveable { mutableStateOf(false) }

    // Collect state from ViewModel
    val registrationState by viewModel.registrationResult.collectAsState()
    val context = LocalContext.current

    val isLoading = registrationState is Resource.Loading
    val isSuccess = registrationState is Resource.Success
    val isError = registrationState is Resource.Error

    // Validation messages
    var fullNameError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

    // Full Name Validation
    val isFullNameValid = fullName.length in 3..30

    // Password Validation
    val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    val isPasswordValid = passwordPattern.matches(password)


    if (isSuccess) {
        LaunchedEffect(Unit) {
            onSignUpSuccess()
            val successMessage = (registrationState as Resource.Success).message
                Toast.makeText(context, successMessage ?: "An error occurred", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),  // Scrollable in case of overflow
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))  // Push content from the top towards the center

        HeaderText(
            text = "Sign Up",
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Start)
        )

        Spacer(Modifier.height(16.dp))

        // Full Name TextField
        LoginTextField(
            value = fullName,
            leadingIcon = Icons.Rounded.Person,
            onValueChange = onFirstNameChange,
            labelText = "Full Name",
            modifier = Modifier.fillMaxWidth()
        )
        // Show Name error only if the button was clicked
        if (fullNameError.isNotEmpty()) {
            Text(
                text = fullNameError,
                color = Color.Red,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        Spacer(Modifier.height(itemSpacing))

        // Email TextField
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

        Spacer(Modifier.height(itemSpacing))

        // Password TextField
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

        Spacer(Modifier.height(itemSpacing))

        // Agreement Checkbox
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val privacyText = "Privacy"
            val policyText = "Policy"
            val annotatedString = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("I Agree with ")
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    pushStringAnnotation(tag = privacyText, privacyText)
                    append(privacyText)
                }
                append(" and ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    pushStringAnnotation(tag = policyText, policyText)
                    append(policyText)
                }
            }

            Checkbox(checked = agree, onCheckedChange = onAgreeChange)

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(offset, offset).forEach {
                        when (it.tag) {
                            privacyText -> onPrivacyClick()
                            policyText -> onPolicyClick()
                        }
                    }
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        // Show progress bar if loading
        if (isLoading) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
        }

        // Sign Up Button
        Button(
            onClick = {
                // Reset error messages
                fullNameError = ""
                passwordError = ""
                emailError = ""

                // Check for validation
                if (!isFullNameValid) {
                    fullNameError = "Name must be between 3 to 30 characters."
                }
                if (email.isEmpty()){
                    emailError = "Email Required!"
                }

                if (!isPasswordValid) {
                    passwordError = "Minimum 8 characters including uppercase, lowercase, number, and special character."
                }


                if (isFullNameValid && isPasswordValid && email.isNotEmpty() && agree) {
                    viewModel.registerUser(
                        RegistrationRequest(
                            fullName = fullName,
                            email = email,
                            password = password
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Sign Up")
        }

        Spacer(Modifier.height(16.dp))

        // Error message display
        if (isError) {
            val errorMessage = (registrationState as Resource.Error).message
            LaunchedEffect(errorMessage) {
                Toast.makeText(context, errorMessage ?: "An error occurred", Toast.LENGTH_LONG).show()
            }
            Text(
                text = errorMessage ?: "An error occurred",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Clickable Sign In Text
        val signInText = "Sign In"
        val signInAnnotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append("Already have an account?")
            }
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                pushStringAnnotation(tag = signInText, signInText)
                append(signInText)
            }
        }

        ClickableText(
            text = signInAnnotatedString,
            onClick = { offset ->
                signInAnnotatedString.getStringAnnotations(offset, offset).forEach {
                    if (it.tag == signInText) {
                        onSignInTextClick()
                    }
                }
            }
        )

        Spacer(Modifier.weight(1f))  // Push content upwards from the bottom towards the center
    }
}





