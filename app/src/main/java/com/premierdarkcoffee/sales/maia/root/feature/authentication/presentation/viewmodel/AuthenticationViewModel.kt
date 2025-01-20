package com.premierdarkcoffee.sales.maia.root.feature.authentication.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.model.PostStoreRequest
import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.usecase.CreateStoreUseCase
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.AddressDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.GeoPointDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.StoreDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.StoreStatusDto
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ImageDto
import com.premierdarkcoffee.sales.maia.root.util.function.getUrlForEndpoint
import com.premierdarkcoffee.sales.maia.root.util.key.getMaiaKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(application: Application, private val createStoreUseCase: CreateStoreUseCase) :
    AndroidViewModel(application) {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val storeKey = getMaiaKey(getApplication<Application>().applicationContext)

    fun signInWithEmail(email: String, password: String, onSuccess: (String) -> Unit, onFailure: (Throwable) -> Unit) {
        if (!isValidEmail(email)) {
            onFailure(IllegalArgumentException("Invalid email address"))
            return
        }

        if (password.isEmpty()) {
            onFailure(IllegalArgumentException("Password cannot be empty"))
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()

                result.user?.let { user ->
                    handleStoreCreation(user.uid, onSuccess, onFailure)
                } ?: throw Exception("User ID is null")
            } catch (e: Exception) {
                Log.e(TAG, "signInWithEmail failed: ${e.message}")
                onFailure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun handleStoreCreation(userId: String,
                                    onSuccess: (token: String) -> Unit,
                                    onFailure: (exception: Throwable) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val store = getFakeStore(userId)
                Log.d(TAG, "handleStoreCreation | Store: $store")

                createStoreUseCase(url = getUrlForEndpoint("cronos-store"),
                                   request = PostStoreRequest(key = storeKey, store = store)).collect { result ->
                    result.onSuccess { response ->
                        Log.d(TAG, "handleStoreCreation | Store created successfully")
                        withContext(Dispatchers.Main) {
                            onSuccess(response.token)
                        }
                    }.onFailure { exception ->
                        Log.e(TAG, "handleStoreCreation | Failed to create store: ${exception.message}")
                        withContext(Dispatchers.Main) {
                            onFailure(exception)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "handleStoreCreation | Exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    onFailure(e)
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }

    companion object {
        private const val TAG = "AuthenticationViewModel"
    }
}


fun getFakeStore(id: String): StoreDto {
    return StoreDto(id = id,
                    name = "Maia Store",
                    image = ImageDto(path = null, url = "", belongs = true),
                    address = AddressDto(street = "...",
                                         city = "...",
                                         state = "...",
                                         zipCode = "...",
                                         country = "...",
                                         location = GeoPointDto(type = "Point", coordinates = listOf(-78.488751, -0.227497))),
                    phoneNumber = "0",
                    emailAddress = "@",
                    website = "www",
                    description = "...",
                    returnPolicy = "...",
                    refundPolicy = "...",
                    brands = emptyList(),
                    createdAt = System.currentTimeMillis(),
                    status = StoreStatusDto(isActive = false,
                                            isVerified = false,
                                            isPromoted = false,
                                            isSuspended = false,
                                            isClosed = false,
                                            isPendingApproval = false,
                                            isUnderReview = false,
                                            isOutOfStock = false,
                                            isOnSale = false))
}
