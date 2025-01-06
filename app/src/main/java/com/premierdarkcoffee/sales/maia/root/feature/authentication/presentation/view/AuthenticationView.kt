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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R

@Composable
fun AuthenticationView(handleSignIn: (String, String) -> Pair<Boolean, String>) {
    // State variables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFEEEEEE)
    val colorScheme = MaterialTheme.colorScheme
    val logo = painterResource(id = R.drawable.logo) // Replace with your logo resource

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
            // Logo
            Image(
                painter = logo,
                contentDescription = "Maia Logo",
                modifier = Modifier
                    .size(200.dp) // Increased logo size
                    .padding(bottom = 24.dp)
            )

            // App Title
            Text(
                text = "Maia",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Welcome Text
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )

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
                label = { Text("Contraseña") },
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
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Iniciar sesión", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forgot Password
            Text(
                text = "Olvidaste tu contraseña",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.clickable { /* Handle Forgot Password */ }
            )
        }

        // Alert Dialog
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                confirmButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Authentication") },
                text = { Text(alertMessage) }
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