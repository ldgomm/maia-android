package com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.viewmodel

//
//  SettingsViewModel.kt
//  Maia
//
//  Created by José Ruiz on 31/7/24.
//

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.premierdarkcoffee.sales.maia.root.feature.settings.domain.state.StoreState
import com.premierdarkcoffee.sales.maia.root.feature.settings.domain.usecase.GetStoreByIdUseCase
import com.premierdarkcoffee.sales.maia.root.util.function.getUrlForEndpoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val getStoreByIdUseCase: GetStoreByIdUseCase) : ViewModel() {
    private val _storeState = MutableStateFlow(StoreState())
    val storeState: StateFlow<StoreState> = _storeState

    private val user = FirebaseAuth.getInstance().currentUser

    fun initData(token: String) {
        getStore(token)
    }

    private fun getStore(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getStoreByIdUseCase(getUrlForEndpoint(endpoint = "maia", storeId = user?.uid), token).collect { result ->
                    result.onSuccess { store ->
                        _storeState.update { storeState ->
                            storeState.copy(store = store.toStore())
                        }
                    }.onFailure { exception ->
                        Log.d(TAG, "SearchViewModel | searchProducts: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "SearchViewModel | Exception in searchProducts: ${e.message}")
            }
        }
    }
}
