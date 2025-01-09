package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.InformationResultState

@Composable
fun InformationCardView(resultState: InformationResultState) {
    // Localized string for accessibility
    val imageDescription = stringResource(id = R.string.information_card_image_description, resultState.title)

    ElevatedCard(
        modifier = Modifier
            .width(250.dp)
            .semantics { contentDescription = "${resultState.title}, ${resultState.subtitle}" },
        shape = RoundedCornerShape(11.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize()
        ) {
            // Image with accessibility support
            Image(
                painter = rememberAsyncImagePainter(resultState.path),
                contentDescription = imageDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Title with scalable text and accessibility
            Text(
                text = resultState.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .semantics { contentDescription = resultState.title }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle with scalable text and accessibility
            Text(
                text = resultState.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .semantics { contentDescription = resultState.subtitle }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description with accessibility and scalability
            Text(
                text = resultState.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .semantics { contentDescription = resultState.description }
            )
        }
    }
}

