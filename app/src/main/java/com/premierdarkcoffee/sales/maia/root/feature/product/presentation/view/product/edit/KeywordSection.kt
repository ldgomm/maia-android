package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit

//
//  KeywordSection.kt
//  Maia
//
//  Created by José Ruiz on 15/1/25.
//

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.AddEditProductState

@Composable
fun KeywordSection(addEditProductState: AddEditProductState,
                   word: String,
                   onWordChange: (String) -> Unit,
                   addKeyword: (String) -> Unit,
                   deleteKeyword: (Int) -> Unit) {
    // Localized strings for accessibility and support
    val keywordHeader = stringResource(id = R.string.keywords_label)
    val enterKeywordHint = stringResource(id = R.string.enter_keyword_hint)
    val addButtonLabel = stringResource(id = R.string.add_button_label)
    val keywordHelperText = stringResource(id = R.string.keyword_helper_text)

    Column {
        // Header with Accessibility
        Text(text = keywordHeader,
             style = MaterialTheme.typography.titleMedium,
             modifier = Modifier
                 .padding(bottom = 8.dp)
                 .semantics { contentDescription = keywordHeader })

        // Input Section with Accessibility
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)) {
            TextField(value = word,
                      onValueChange = onWordChange,
                      label = { Text(enterKeywordHint) },
                      modifier = Modifier
                          .weight(1f)
                          .padding(end = 8.dp)
                          .semantics { contentDescription = enterKeywordHint })

            Button(onClick = {
                addKeyword(word)
                onWordChange("")
            },
                   enabled = word.isNotEmpty(),
                   modifier = Modifier
                       .align(Alignment.CenterVertically)
                       .semantics { contentDescription = addButtonLabel }) {
                Text(addButtonLabel)
            }
        }

        // Keyword List Section with LazyRow for Accessibility
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(addEditProductState.keywords) { index, keyword ->
                KeywordBubble(keyword = keyword) {
                    deleteKeyword(index)
                }
            }
        }

        // Footer with Accessibility
        Text(text = keywordHelperText,
             style = MaterialTheme.typography.labelSmall,
             modifier = Modifier
                 .padding(top = 8.dp)
                 .semantics { contentDescription = keywordHelperText })
    }
}
