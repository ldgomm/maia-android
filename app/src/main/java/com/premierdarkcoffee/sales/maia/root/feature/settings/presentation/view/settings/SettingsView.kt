package com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.view.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.util.helper.JwtSecurePreferencesHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(
    onNavigateToPrivacyPolicyButtonClicked: () -> Unit,
    onNavigateToTermsOfUseButtonClicked: () -> Unit,
    onNavigateToAccountDeletionButtonClicked: () -> Unit,
    onLogoutButtonClicked: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.settings_title)) })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Section 1: Privacy Policy and Terms and Conditions
            SettingsSection(
                title = stringResource(id = R.string.general_title),
                items = listOf(
                    stringResource(id = R.string.privacy_policy) to onNavigateToPrivacyPolicyButtonClicked,
                    stringResource(id = R.string.terms_of_use) to onNavigateToTermsOfUseButtonClicked,
                    stringResource(id = R.string.account_deletion) to onNavigateToAccountDeletionButtonClicked
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Section 2: Log Out Button
            SettingsSection(
                title = stringResource(id = R.string.account_title),
                items = listOf(
                    stringResource(id = R.string.log_out) to {
                        logOut(context)
                        onLogoutButtonClicked()
                    }
                ),
                buttonColors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            )
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    items: List<Pair<String, () -> Unit>>,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Text(
        text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp)
    )

    items.forEach { (label, action) ->
        Button(
            onClick = action, colors = buttonColors, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(label, modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiary, textAlign = TextAlign.Start)
        }
    }
}

fun logOut(context: Context) {
    // Delete JWT token from secure storage
    JwtSecurePreferencesHelper.deleteJwt(context)

    // Try to sign out of Firebase
    try {
        FirebaseAuth.getInstance().signOut()
    } catch (e: Exception) {
        // Handle log out failure
        println("Error signing out: ${e.localizedMessage}")
    }
}
