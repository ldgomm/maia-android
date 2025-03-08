package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.components

//
//  DoubleTextFieldCard.kt
//  Maia
//
//  Created by José Ruiz on 1/4/25.
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun DoubleTextFieldCard(label: String,
                        value: Double,
                        onValueChange: (Double) -> Unit,
                        readOnly: Boolean = false,
                        enabled: Boolean = true,
                        decimals: Int = 2,
                        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
                        onClick: (() -> Unit)? = null) {
    // Configure how many decimal places you allow
    val decimalFormat = remember(decimals) {
        DecimalFormat().apply {
            minimumFractionDigits = 0          // Let the user type e.g. "12." if they want
            maximumFractionDigits = decimals   // Cap the displayed decimals
            roundingMode = RoundingMode.HALF_UP
        }
    }

    // Local state for the TextField input
    // Initialize it with a formatted version of the incoming value
    val textState = rememberSaveable { mutableStateOf(decimalFormat.format(value)) }

    // When `value` changes from elsewhere (e.g., external state update),
    // update our local text if it's different than the user’s typed text
    LaunchedEffect(value) {
        val formattedValue = decimalFormat.format(value)
        if (textState.value != formattedValue) {
            textState.value = formattedValue
        }
    }

    ElevatedCard(modifier = Modifier
        .clickable { onClick?.invoke() }
        .fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 11.dp),
                 shape = MaterialTheme.shapes.medium,
                 elevation = CardDefaults.elevatedCardElevation(4.dp)) {
        TextField(
                // Use our local text state
                value = textState.value,
                onValueChange = { newText ->
                    // Update the local state immediately
                    textState.value = newText

                    // Attempt to parse it as a Double
                    val parsedDouble = newText.toDoubleOrNull()
                    if (parsedDouble != null) {
                        onValueChange(parsedDouble)
                    }
                    // If `parsedDouble` is null, we do not call onValueChange yet,
                    // allowing the user to fix partial input (like "12.")
                },
                label = { Text(label) },
                keyboardOptions = keyboardOptions,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = label },
                readOnly = readOnly,
                enabled = enabled)
    }
}
