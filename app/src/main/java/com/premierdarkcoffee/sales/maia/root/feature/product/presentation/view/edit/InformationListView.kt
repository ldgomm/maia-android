package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.InformationResultState

@Composable
fun InformationListView(informationResultStateList: List<InformationResultState>) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 11.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = informationResultStateList.filterNot { it.isDeleted }, key = { it.id }) { resultState ->
            InformationCardView(resultState = resultState)
        }
    }
}
