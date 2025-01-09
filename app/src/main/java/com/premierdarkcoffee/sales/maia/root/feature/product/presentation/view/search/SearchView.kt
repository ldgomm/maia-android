package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.titleStyle
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.ProductsState
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.ProductCardView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    productsState: ProductsState,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    clearSearchText: () -> Unit,
    onNavigateToProductView: (String) -> Unit
) {
    var active by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyListState = rememberLazyListState()

    val searchTitle = stringResource(id = R.string.search_title)
    val searchPlaceholder = stringResource(id = R.string.search_placeholder)
    val clearSearchTextLabel = stringResource(id = R.string.clear_search_text)
    val noProductsFound = stringResource(id = R.string.no_products_found)

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding()
        .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(title = {
            Text(text = searchTitle, modifier = Modifier.fillMaxWidth(), style = titleStyle)
        }, modifier = Modifier, navigationIcon = {}, actions = {})
    }) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateRightPadding(LayoutDirection.Ltr)
                )
        ) {
            SearchBar(query = searchText,
                      onQueryChange = onSearchTextChange,
                      onSearch = { active = false },
                      active = active,
                      onActiveChange = {
                          active = it
                      },
                      modifier = Modifier
                          .fillMaxWidth()
                          .padding(horizontal = 10.dp),
                      placeholder = { Text(searchPlaceholder) },
                      leadingIcon = { Icon(imageVector = Icons.Default.Search, null) },
                      trailingIcon = {
                          if (active) {
                              Icon(imageVector = Icons.Default.Close, contentDescription = clearSearchTextLabel, modifier = Modifier.clickable {
                                  if (searchText.isNotEmpty()) {
                                      clearSearchText()
                                  } else {
                                      active = false
                                  }
                              })
                          }
                      }) {
                LazyColumn(
                    state = lazyListState, modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    productsState.products?.let { products ->
                        items(products.size) { index ->
                            val product = products[index]
                            ProductCardView(product = product, onNavigateToProductView)
                        }
                    }
                }
            }
            LazyColumn(
                state = lazyListState, modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
            ) {
                productsState.products?.let { products ->
                    items(products.size) { index ->
                        val product = products[index]
                        ProductCardView(product = product, onNavigateToProductView)
                    }
                }
            }
        }
    }
}
