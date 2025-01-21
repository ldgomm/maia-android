package com.premierdarkcoffee.sales.maia.root.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.auth.FirebaseAuth
import com.premierdarkcoffee.sales.maia.R.drawable.chat
import com.premierdarkcoffee.sales.maia.R.drawable.storefront
import com.premierdarkcoffee.sales.maia.root.util.function.shouldShowNavigationBar

@Composable
fun MainView(navController: NavHostController, startDestination: Any) {
    var user by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }

    FirebaseAuth.getInstance().addAuthStateListener { auth ->
        user = auth.currentUser
    }

    Scaffold(bottomBar = {
        AnimatedVisibility(visible = user != null && shouldShowNavigationBar(navController = navController),
                           enter = fadeIn(animationSpec = tween(durationMillis = 300)) + slideInVertically(initialOffsetY = { it }),
                           exit = fadeOut(animationSpec = tween(durationMillis = 300)) + slideOutVertically(targetOffsetY = { it })) {
            val items = listOf(ProductsRoute,
                               SearchRoute,
                               ChatsRoute.apply { icon = ImageVector.vectorResource(chat) },
                               StoreRoute.apply { icon = ImageVector.vectorResource(storefront) })
            NavigationBar(modifier = Modifier.animateContentSize()) {
                items.forEach { item ->
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    NavigationBarItem(icon = {
                        Icon(imageVector = item.icon!!, contentDescription = stringResource(id = item.resourceId))
                    },
                                      label = { Text(text = stringResource(item.resourceId)) },
                                      selected = currentDestination?.hierarchy?.any { it == item } == true,
                                      onClick = {
                                          navController.navigate(item) {
                                              popUpTo(navController.graph.findStartDestination().id) {
                                                  saveState = true
                                              }
                                              launchSingleTop = true
                                              restoreState = true
                                          }
                                      },
                                      modifier = Modifier)
                }
            }
        }
    }) { paddingValues ->
        NavigationGraph(navController = navController, startDestination = startDestination, Modifier.padding(paddingValues))
    }
}
