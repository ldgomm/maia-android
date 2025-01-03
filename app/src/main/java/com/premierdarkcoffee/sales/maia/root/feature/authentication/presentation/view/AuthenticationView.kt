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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R

@Composable
fun AuthenticationView(handleSignIn: (String, String) -> Pair<Boolean, String>) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFEEEEEE)
    val colorScheme = MaterialTheme.colorScheme
    val logo = painterResource(id = R.drawable.logo)

    // Pre-loading localized strings for reuse and accessibility support
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo with proper accessibility support
            Image(
                painter = logo,
                contentDescription = logoDescriptionText,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 24.dp)
                    .testTag("logoImage")
            )

            // App Title with semantic support
            Text(
                text = appNameText,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .semantics { heading() }
            )

            // Welcome Text with content description
            Text(
                text = welcomeMessageText,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email Input Field with accessibility support
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(emailLabelText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = emailLabelText
                    },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                isError = email.isEmpty()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input Field with accessibility support
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

            // Sign-in Button with content description and accessibility features
            Button(
                onClick = {
                    val (success, message) = handleSignIn(email, password)
                    alertMessage = if (success) signInSuccessfulText else message
                    showAlert = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .semantics { contentDescription = signInButtonText }
            ) {
                Text(signInButtonText, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forgot Password clickable text with accessibility support
            Text(
                text = forgotPasswordText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier
                    .clickable { /* Handle Forgot Password */ }
                    .semantics { contentDescription = forgotPasswordText }
            )
        }

        // Alert Dialog with proper accessibility
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                confirmButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text(okButtonText)
                    }
                },
                title = { Text(authTitleText) },
                text = { Text(alertMessage) },
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Assertive }
            )
        }
    }
}


@Preview
@Composable
private fun AuthenticationView_Preview() {
    AuthenticationView(handleSignIn = { email, password ->
        Pair(false, "Something went wrong. Please try again.")
    })
}