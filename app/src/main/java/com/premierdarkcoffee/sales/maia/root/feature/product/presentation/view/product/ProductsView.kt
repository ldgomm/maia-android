package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.titleStyle
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.Group
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.ProductsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsView(
    productsState: ProductsState,
    searchProductText: String,
    groups: List<Group>,
    onSearchTextChange: (String) -> Unit,
    clearSearchText: () -> Unit,
    onNavigateToProductView: (String) -> Unit,
    onRefresh: () -> Unit
) {
    var active by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    // State variables for filtering
    var selectedGroup by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedDomain by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedSubclass by rememberSaveable { mutableStateOf<String?>(null) }

    // Variables to control the dropdown menus
    var groupExpanded by remember { mutableStateOf(false) }
    var domainExpanded by remember { mutableStateOf(false) }
    var subclassExpanded by remember { mutableStateOf(false) }

    // Get domains and subclasses based on selections
    val selectedGroupObj = groups.find { it.name == selectedGroup }
    val domains = selectedGroupObj?.domains ?: emptyList()
    val selectedDomainObj = domains.find { it.name == selectedDomain }
    val subclasses = selectedDomainObj?.subclasses ?: emptyList()

    // Filter products based on selections and search text
    val filteredProducts = productsState.products?.filter { product ->
        (selectedGroup == null || product.category.group == selectedGroup) && (selectedDomain == null || product.category.domain == selectedDomain) && (selectedSubclass == null || product.category.subclass == selectedSubclass) && (searchProductText.isEmpty() || product.name.contains(
            searchProductText,
            ignoreCase = true
        ))
    }

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding()
        .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "${stringResource(id = R.string.products_label)} (${filteredProducts?.size ?: 0})", style = titleStyle
                )
            }, scrollBehavior = scrollBehavior
        )
    }) { paddingValues ->
        SwipeRefresh(
            state = swipeRefreshState, onRefresh = onRefresh, modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Adjust padding as needed
            ) {
                // Search Bar
                SearchBar(query = searchProductText,
                          onQueryChange = onSearchTextChange,
                          onSearch = { active = false },
                          active = active,
                          onActiveChange = { active = it },
                          modifier = Modifier.fillMaxWidth(),
                          placeholder = { Text("Search products") },
                          leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                          trailingIcon = {
                              if (active) {
                                  Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier.clickable {
                                      if (searchProductText.isNotEmpty()) {
                                          clearSearchText()
                                      } else {
                                          active = false
                                      }
                                  })
                              }
                          }) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        productsState.products?.let { products ->
                            items(products) { product ->
                                ProductCardView(product = product, onNavigateToProductView)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Filters Row
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Group Dropdown
                    ExposedDropdownMenuBox(
                        expanded = groupExpanded, onExpandedChange = { groupExpanded = !groupExpanded }, modifier = Modifier.weight(1f)
                    ) {
                        TextField(
                            value = selectedGroup?.substring(0, 5) ?: "All",
                            onValueChange = {},
//                            label = { Text("Group") },
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = groupExpanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(expanded = groupExpanded, onDismissRequest = { groupExpanded = false }) {
                            groups.forEach { group ->
                                DropdownMenuItem(text = { Text(text = group.name) }, onClick = {
                                    selectedGroup = group.name
                                    selectedDomain = null
                                    selectedSubclass = null
                                    groupExpanded = false
                                })
                            }
                        }
                    }

                    // Domain Dropdown
                    if (selectedGroup != null) {
                        ExposedDropdownMenuBox(
                            expanded = domainExpanded, onExpandedChange = { domainExpanded = !domainExpanded }, modifier = Modifier.weight(1f)
                        ) {
                            TextField(
                                value = selectedDomain?.substring(0, 5) ?: "All",
                                onValueChange = {},
//                                label = { Text("Domain") },
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = domainExpanded) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(expanded = domainExpanded, onDismissRequest = { domainExpanded = false }) {
                                domains.forEach { domain ->
                                    DropdownMenuItem(text = { Text(text = domain.name) }, onClick = {
                                        selectedDomain = domain.name
                                        selectedSubclass = null
                                        domainExpanded = false
                                    })
                                }
                            }
                        }
                    }

                    // Subclass Dropdown
                    if (selectedDomain != null) {
                        ExposedDropdownMenuBox(
                            expanded = subclassExpanded, onExpandedChange = { subclassExpanded = !subclassExpanded }, modifier = Modifier.weight(1f)
                        ) {
                            TextField(
                                value = selectedSubclass?.substring(0, 5) ?: "All",
                                onValueChange = {},
//                                label = { Text("Subclass") },
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subclassExpanded) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(expanded = subclassExpanded, onDismissRequest = { subclassExpanded = false }) {
                                subclasses.forEach { subclass ->
                                    DropdownMenuItem(text = { Text(text = subclass.name) }, onClick = {
                                        selectedSubclass = subclass.name
                                        subclassExpanded = false
                                    })
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Display filtered products
                if (filteredProducts.isNullOrEmpty()) {
                    Text("No products found", style = MaterialTheme.typography.bodyLarge)
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredProducts) { product ->
                            ProductCardView(product = product, onNavigateToProductView)
                        }
                    }
                }
            }
        }
    }
}
