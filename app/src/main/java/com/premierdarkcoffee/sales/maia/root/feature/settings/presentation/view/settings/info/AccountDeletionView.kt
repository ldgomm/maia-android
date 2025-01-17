package com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.view.settings.info

//
//  AccountDeletionView.kt
//  Maia
//
//  Created by José Ruiz on 18/10/24.
//

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDeletionView(modifier: Modifier = Modifier) {
    // Localized strings for multilingual support and accessibility
    val accountDeletionTitle = stringResource(id = R.string.account_deletion_title)
    val deletionProcessText = stringResource(id = R.string.deletion_process_text)
    val importantNoteText = stringResource(id = R.string.important_note_text)
    val backupRecommendationText = stringResource(id = R.string.backup_recommendation_text)
    val contactInformationText = stringResource(id = R.string.contact_information_text)
    val responseTimeNoticeText = stringResource(id = R.string.response_time_notice_text)
    val footerText = stringResource(id = R.string.footer_text)

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = accountDeletionTitle) })
    }) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Deletion Process
            item {
                Text(
                    text = deletionProcessText, style = MaterialTheme.typography.bodyMedium
                )
            }

            // Section 2: Important Note
            item {
                Text(
                    text = importantNoteText, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold
                )
            }

            // Section 3: Backup Recommendation
            item {
                Text(
                    text = backupRecommendationText, style = MaterialTheme.typography.bodyMedium
                )
            }

            // Section 4: Contact Information with clickable email
            item {
                ClickableText(text = AnnotatedString(contactInformationText),
                              style = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue),
                              onClick = { /* Implement an intent to open an email app */ })
            }

            // Section 5: Response Time Notice
            item {
                Text(
                    text = responseTimeNoticeText, style = MaterialTheme.typography.bodyMedium
                )
            }

            // Footer Section
            item {
                Text(
                    text = footerText, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 50.dp)
                )
            }
        }
    }
}
