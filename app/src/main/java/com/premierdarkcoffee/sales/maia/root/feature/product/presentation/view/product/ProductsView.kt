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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.titleStyle
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.Group
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.ProductsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsView(productsState: ProductsState,
                 searchProductText: String,
                 groups: List<Group>,
                 onSearchTextChange: (String) -> Unit,
                 clearSearchText: () -> Unit,
                 onNavigateToProductView: (String) -> Unit,
                 onRefresh: () -> Unit) {
    var active by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    // -- State variables for filtering --
    var selectedGroup by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedDomain by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedSubclass by rememberSaveable { mutableStateOf<String?>(null) }

    // -- Variables to control the dropdown menus --
    var groupExpanded by remember { mutableStateOf(false) }
    var domainExpanded by remember { mutableStateOf(false) }
    var subclassExpanded by remember { mutableStateOf(false) }

    // Grab all products (or empty list if null)
    val allProducts = productsState.products ?: emptyList()

    // -- 1. Filter Groups that contain at least one product --
    val filteredGroups = groups.filter { group ->
        allProducts.any { product -> product.category.group == group.name }
    }

    // Find the group object that matches the user’s selection, if any
    val selectedGroupObj = filteredGroups.find { it.name == selectedGroup }

    // -- 2. Filter Domains within the selected Group that contain at least one product --
    val filteredDomains = selectedGroupObj?.domains?.filter { domain ->
        allProducts.any { product ->
            product.category.group == selectedGroup && product.category.domain == domain.name
        }
    } ?: emptyList()

    // Find the domain object that matches the user’s selection, if any
    val selectedDomainObj = filteredDomains.find { it.name == selectedDomain }

    // -- 3. Filter Subclasses within the selected Domain that contain at least one product --
    val filteredSubclasses = selectedDomainObj?.subclasses?.filter { subclass ->
        allProducts.any { product ->
            product.category.group == selectedGroup && product.category.domain == selectedDomain && product.category.subclass == subclass.name
        }
    } ?: emptyList()

    // -- Finally, filter products based on selections + search text --
    val filteredProducts = productsState.products?.filter { product ->
        (selectedGroup == null || product.category.group == selectedGroup) && (selectedDomain == null || product.category.domain == selectedDomain) && (selectedSubclass == null || product.category.subclass == selectedSubclass) && (searchProductText.isEmpty() || product.name.contains(
                searchProductText,
                ignoreCase = true))
    }

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding()
        .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        val productsTitle = stringResource(id = R.string.products_title)
        TopAppBar(title = {
            Text(text = "$productsTitle (${filteredProducts?.size ?: 0})",
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis,
                 style = titleStyle)
        }, scrollBehavior = scrollBehavior)
    }) { paddingValues ->
        SwipeRefresh(state = swipeRefreshState, onRefresh = onRefresh, modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Column(Modifier
                       .fillMaxSize()
                       .padding(16.dp)) {
                // -- Search Bar --
                val searchPlaceholder = stringResource(id = R.string.search_placeholder)
                val clearSearchTextX = stringResource(id = R.string.clear_search)

                SearchBar(query = searchProductText,
                          onQueryChange = onSearchTextChange,
                          onSearch = { active = false },
                          active = active,
                          onActiveChange = { active = it },
                          modifier = Modifier
                              .fillMaxWidth()
                              .semantics { contentDescription = searchPlaceholder },
                          placeholder = { Text(searchPlaceholder) },
                          leadingIcon = {
                              Icon(imageVector = Icons.Default.Search, contentDescription = searchPlaceholder)
                          },
                          trailingIcon = {
                              if (active) {
                                  Icon(imageVector = Icons.Default.Close,
                                       contentDescription = clearSearchTextX,
                                       modifier = Modifier.clickable {
                                           if (searchProductText.isNotEmpty()) {
                                               clearSearchText()
                                           } else {
                                               active = false
                                           }
                                       })
                              }
                          }) {
                    // If the user has tapped on the SearchBar, show an expanded list
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        productsState.products?.let { products ->
                            items(products) { product ->
                                ProductCardView(product = product, onNavigateToProductView)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // -- Filters Row (Group, Domain, Subclass) --
                val allGroupsLabel = stringResource(id = R.string.all_groups)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Group Dropdown
                    ExposedDropdownMenuBox(expanded = groupExpanded,
                                           onExpandedChange = { groupExpanded = !groupExpanded },
                                           modifier = Modifier.weight(1f)) {
                        TextField(value = selectedGroup ?: allGroupsLabel,
                                  onValueChange = {},
                                  readOnly = true,
                                  trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = groupExpanded) },
                                  modifier = Modifier.menuAnchor())
                        ExposedDropdownMenu(expanded = groupExpanded, onDismissRequest = { groupExpanded = false }) {
                            filteredGroups.forEach { group ->
                                DropdownMenuItem(text = { Text(group.name) }, onClick = {
                                    selectedGroup = group.name
                                    selectedDomain = null
                                    selectedSubclass = null
                                    groupExpanded = false
                                })
                            }
                        }
                    }

                    // Domain Dropdown (Conditionally Displayed)
                    if (selectedGroup != null) {
                        ExposedDropdownMenuBox(expanded = domainExpanded,
                                               onExpandedChange = { domainExpanded = !domainExpanded },
                                               modifier = Modifier.weight(1f)) {
                            TextField(value = selectedDomain ?: allGroupsLabel,
                                      onValueChange = {},
                                      readOnly = true,
                                      trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = domainExpanded) },
                                      modifier = Modifier.menuAnchor())
                            ExposedDropdownMenu(expanded = domainExpanded, onDismissRequest = { domainExpanded = false }) {
                                filteredDomains.forEach { domain ->
                                    DropdownMenuItem(text = { Text(domain.name) }, onClick = {
                                        selectedDomain = domain.name
                                        selectedSubclass = null
                                        domainExpanded = false
                                    })
                                }
                            }
                        }
                    }

                    // Subclass Dropdown (Conditionally Displayed)
                    if (selectedDomain != null) {
                        ExposedDropdownMenuBox(expanded = subclassExpanded,
                                               onExpandedChange = { subclassExpanded = !subclassExpanded },
                                               modifier = Modifier.weight(1f)) {
                            TextField(value = selectedSubclass ?: allGroupsLabel,
                                      onValueChange = {},
                                      readOnly = true,
                                      trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subclassExpanded) },
                                      modifier = Modifier.menuAnchor())
                            ExposedDropdownMenu(expanded = subclassExpanded, onDismissRequest = { subclassExpanded = false }) {
                                filteredSubclasses.forEach { subclass ->
                                    DropdownMenuItem(text = { Text(subclass.name) }, onClick = {
                                        selectedSubclass = subclass.name
                                        subclassExpanded = false
                                    })
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // -- Display filtered products or "No products found" message --
                val noProductsFound = stringResource(id = R.string.no_products_found)

                if (filteredProducts.isNullOrEmpty()) {
                    Text(text = noProductsFound,
                         style = MaterialTheme.typography.bodyLarge,
                         modifier = Modifier.semantics { contentDescription = noProductsFound })
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
