package com.premierdarkcoffee.sales.maia.root.feature.authentication.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthenticationView(handleSignIn: (String, String) -> Pair<Boolean, String>) {
    // State variables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    val colorScheme = MaterialTheme.colorScheme

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Title
            Text(
                text = "Maia",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Welcome Text
            Column(
                modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Welcome Back", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold, color = colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sign in to continue to Maia",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Input Fields
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                isError = email.isEmpty()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = password.isEmpty()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sign-in Button
            Button(
                onClick = {
                    val (success, message) = handleSignIn(email, password)
                    alertMessage = if (success) "Sign-in successful!" else message
                    showAlert = true
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Text("Sign In")
            }
        }

        // Alert Dialog
        if (showAlert) {
            AlertDialog(onDismissRequest = { showAlert = false }, confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text("OK")
                }
            }, title = { Text("Authentication") }, text = { Text(alertMessage) })
        }
    }
}
