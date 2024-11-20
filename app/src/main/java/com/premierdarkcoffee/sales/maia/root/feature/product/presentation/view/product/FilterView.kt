package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.Domain
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.Group
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.Subclass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterView(
    selectedGroup: Group?,
    onSelectedGroupChange: (Group?) -> Unit,
    selectedDomain: Domain?,
    onSelectedDomainChange: (Domain?) -> Unit,
    selectedSubclass: Subclass?,
    onSelectedSubclassChange: (Subclass?) -> Unit,
    groups: List<Group>,
    products: List<Product>?
) {
    val nonEmptyGroups = groups.filter { groupHasProducts(it, products) }

    val nonEmptyDomains = selectedGroup?.domains?.filter { domainHasProducts(it, selectedGroup, products) } ?: emptyList()

    val nonEmptySubclasses =
        selectedDomain?.subclasses?.filter { subclassHasProducts(it, selectedGroup, selectedDomain, products) } ?: emptyList()

    var groupExpanded by remember { mutableStateOf(false) }
    var domainExpanded by remember { mutableStateOf(false) }
    var subclassExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // Group Picker
        ExposedDropdownMenuBox(expanded = groupExpanded, onExpandedChange = { groupExpanded = !groupExpanded }) {
            OutlinedTextField(
                value = selectedGroup?.name ?: "All",
                onValueChange = {},
                readOnly = true,
                label = { Text("Group") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = groupExpanded) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = groupExpanded, onDismissRequest = { groupExpanded = false }) {
                DropdownMenuItem(text = { Text("All") }, onClick = {
                    onSelectedGroupChange(null)
                    groupExpanded = false
                })
                nonEmptyGroups.sortedBy { it.name }.forEach { group ->
                    DropdownMenuItem(text = { Text("All") }, onClick = {
                        onSelectedGroupChange(group)
                        groupExpanded = false
                    })
                }
            }
        }

        // Domain Picker
        if (selectedGroup != null) {
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(expanded = domainExpanded, onExpandedChange = { domainExpanded = !domainExpanded }) {
                OutlinedTextField(
                    value = selectedDomain?.name ?: "All",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Domain") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = domainExpanded) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = domainExpanded, onDismissRequest = { domainExpanded = false }) {
                    DropdownMenuItem(text = { Text("All") }, onClick = {
                        onSelectedDomainChange(null)
                        domainExpanded = false
                    })
                    nonEmptyDomains.sortedBy { it.name }.forEach { domain ->
                        DropdownMenuItem(text = { Text("All") }, onClick = {
                            onSelectedDomainChange(domain)
                            domainExpanded = false
                        })
                    }
                }
            }
        }

        // Subclass Picker
        if (selectedGroup != null && selectedDomain != null) {
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(expanded = subclassExpanded, onExpandedChange = { subclassExpanded = !subclassExpanded }) {
                OutlinedTextField(
                    value = selectedSubclass?.name ?: "All",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Subclass") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subclassExpanded) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = subclassExpanded, onDismissRequest = { subclassExpanded = false }) {
                    DropdownMenuItem(text = { Text("All") }, onClick = {
                        onSelectedSubclassChange(null)
                        subclassExpanded = false
                    })
                    nonEmptySubclasses.sortedBy { it.name }.forEach { subclass ->
                        DropdownMenuItem(text = { Text("All") }, onClick = {
                            onSelectedSubclassChange(subclass)
                            subclassExpanded = false
                        })
                    }
                }
            }
        }
    }
}

fun groupHasProducts(
    group: Group,
    products: List<Product>?
): Boolean {
    return group.domains.any { domainHasProducts(it, group, products) }
}

fun domainHasProducts(
    domain: Domain,
    group: Group,
    products: List<Product>?
): Boolean {
    return domain.subclasses.any { subclassHasProducts(it, group, domain, products) }
}

fun subclassHasProducts(
    subclass: Subclass,
    group: Group?,
    domain: Domain,
    products: List<Product>?
): Boolean {
    return products?.any { product ->
        product.category.group == group?.name && product.category.domain == domain.name && product.category.subclass == subclass.name
    } ?: false
}
