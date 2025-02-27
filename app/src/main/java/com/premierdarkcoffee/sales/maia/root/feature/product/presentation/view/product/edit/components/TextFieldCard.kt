package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.components

//
//  TextFieldCard.kt
//  Maia
//
//  Created by José Ruiz on 16/1/25.
//

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldCard(label: String,
                  text: String,
                  onTextChanged: (String) -> Unit,
                  readOnly: Boolean = false,
                  enabled: Boolean = true,
                  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,

                  onClick: (() -> Unit)? = null) {
    ElevatedCard(modifier = Modifier
        .clickable { onClick?.invoke() }
        .fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 11.dp),
                 shape = MaterialTheme.shapes.medium,
                 elevation = CardDefaults.elevatedCardElevation(4.dp)) {
        TextField(value = text,
                  onValueChange = onTextChanged,
                  label = { Text(label) },
                  keyboardOptions = keyboardOptions,
                  modifier = Modifier
                      .fillMaxWidth()
                      .semantics { contentDescription = label },
                  readOnly = readOnly,
                  enabled = enabled)
    }
}