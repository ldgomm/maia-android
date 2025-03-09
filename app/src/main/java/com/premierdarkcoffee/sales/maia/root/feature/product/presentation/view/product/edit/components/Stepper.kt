package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.components

//
//  Stepper.kt
//  Maia
//
//  Created by José Ruiz on 1/4/25.
//

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Stepper(value: Int,
            onValueChange: (Int) -> Unit,
            valueRange: IntRange = 0..100,
            step: Int = 1,
            modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        // Decrement Button
        Button(onClick = { if (value > valueRange.first) onValueChange(value - step) }, enabled = value > valueRange.first) {
            Text("-")
        }

        // Current Value
        Text(text = value.toString(), modifier = Modifier.padding(horizontal = 16.dp))

        // Increment Button
        Button(onClick = { if (value < valueRange.last) onValueChange(value + step) }, enabled = value < valueRange.last) {
            Text("+")
        }
    }
}
