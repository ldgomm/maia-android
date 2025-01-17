package com.premierdarkcoffee.sales.maia.root.feature.authentication.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R

@Composable
fun AuthenticationView(
    handleSignIn: (String, String) -> Unit,
    signInResult: Pair<Boolean, String>
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFEEEEEE)
    val colorScheme = MaterialTheme.colorScheme
    val logo = painterResource(id = R.drawable.logo)

    // Pre-loading localized strings (same as before)
    val signInSuccessfulText = stringResource(id = R.string.sign_in_successful)
    val signInButtonText = stringResource(id = R.string.sign_in_button)
    val emailLabelText = stringResource(id = R.string.email_label)
    val passwordLabelText = stringResource(id = R.string.password_label)
    val forgotPasswordText = stringResource(id = R.string.forgot_password)
    val authTitleText = stringResource(id = R.string.auth_title)
    val okButtonText = stringResource(id = R.string.ok_button)
    val welcomeMessageText = stringResource(id = R.string.welcome_message)
    val appNameText = stringResource(id = R.string.app_name)
    val logoDescriptionText = stringResource(id = R.string.logo_description)

    // Whenever signInResult changes, decide if we should show an alert
    LaunchedEffect(signInResult) {
        // Only show an alert if the message is not the "Default" or if you prefer to always show
        if (signInResult.second != "Default message.") {
            // If success, we could override the message or keep signInResult as is
            alertMessage = if (signInResult.first) {
                signInSuccessfulText
            } else {
                signInResult.second
            }
            showAlert = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = logo, contentDescription = logoDescriptionText, modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 24.dp)
                    .testTag("logoImage")
            )

            // App Title
            Text(text = appNameText,
                 style = MaterialTheme.typography.headlineLarge,
                 fontWeight = FontWeight.Bold,
                 color = colorScheme.primary,
                 modifier = Modifier
                     .padding(bottom = 8.dp)
                     .semantics { heading() })

            Text(
                text = welcomeMessageText, style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email Input
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text(emailLabelText) }, modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = emailLabelText
                }, singleLine = true, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email), isError = email.isEmpty()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(passwordLabelText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = passwordLabelText },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = password.isEmpty()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sign-In Button
            Button(onClick = {
                // Invoke the callback
                handleSignIn(email, password)
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .semantics { contentDescription = signInButtonText }) {
                Text(signInButtonText, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forgot Password
            Text(text = forgotPasswordText,
                 style = MaterialTheme.typography.bodyMedium,
                 color = Color.Gray,
                 modifier = Modifier
                     .clickable { /* Handle Forgot Password */ }
                     .semantics { contentDescription = forgotPasswordText })
        }

        // Alert Dialog
        if (showAlert) {
            AlertDialog(onDismissRequest = { showAlert = false }, confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text(okButtonText)
                }
            }, title = { Text(authTitleText) }, text = { Text(alertMessage) }, modifier = Modifier.semantics { liveRegion = LiveRegionMode.Assertive })
        }
    }
}
