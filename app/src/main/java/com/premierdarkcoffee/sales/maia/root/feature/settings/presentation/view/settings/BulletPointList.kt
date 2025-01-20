package com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.view.settings

//
//  BulletPointList.kt
//  Maia
//
//  Created by José Ruiz on 18/10/24.
//

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun BulletPointList(bulletPoints: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        bulletPoints.forEach { point ->
            Row(modifier = Modifier.semantics { contentDescription = "Bullet Point" }) {
                Text("•", modifier = Modifier.padding(end = 8.dp))
                Text(text = point, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
