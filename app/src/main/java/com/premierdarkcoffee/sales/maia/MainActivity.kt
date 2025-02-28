package com.premierdarkcoffee.sales.maia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.premierdarkcoffee.sales.maia.root.navigation.AuthenticationRoute
import com.premierdarkcoffee.sales.maia.root.navigation.MainView
import com.premierdarkcoffee.sales.maia.root.navigation.ProductsRoute
import com.premierdarkcoffee.sales.maia.root.util.helper.ApiKeySecurePreferencesHelper
import com.premierdarkcoffee.sales.maia.root.util.theme.MaiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = BuildConfig.API_KEY
        ApiKeySecurePreferencesHelper.storeApiKey(this, apiKey)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        networkMonitor = NetworkMonitor(this)

        setContent {
            val navController = rememberNavController()
            val user = FirebaseAuth.getInstance().currentUser
            val startDestination = if (user != null) ProductsRoute else AuthenticationRoute
            val isConnected by networkMonitor.observeAsState(true)

            MaiaTheme {
                when {
                    !isConnected -> NoInternetView()
                    user != null -> MainView(navController = navController, startDestination = startDestination)
                    else -> {
                        UnstableConnectionView()
                    }
                }
            }
        }
    }
}
