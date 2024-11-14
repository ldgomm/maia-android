package com.premierdarkcoffee.sales.maia.root.feature.authentication.presentation.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.model.PostStoreRequest
import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.usecase.CreateStoreUseCase
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.AddressDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.GeoPointDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.StoreDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.StoreStatusDto
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ImageDto
import com.premierdarkcoffee.sales.maia.root.util.function.getUrlFor
import com.premierdarkcoffee.sales.maia.root.util.key.getMaiaKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    application: Application,
    private val createStoreUseCase: CreateStoreUseCase
) : AndroidViewModel(application) {

    var signedInState = mutableStateOf(false)
        private set

    private val storeKey = getMaiaKey(getApplication<Application>().applicationContext)

    fun setSignInState(state: Boolean) {
        signedInState.value = state
    }

    fun signInWithFirebase(
        tokenId: String,
        onSuccess: (String) -> Unit,
        onFailure: (exception: Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(tokenId, null)
                val instance = FirebaseAuth.getInstance()
                val task = instance.signInWithCredential(credential).await()
                task.user?.let { user ->
                    setSignInState(true)
                    handleStoreCreation(user.uid, onSuccess, onFailure)
                } ?: run {
                    onFailure(Exception("User ID is null"))
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    private fun handleStoreCreation(
        userId: String,
        onSuccess: (token: String) -> Unit,
        onFailure: (exception: Throwable) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val store: StoreDto = getFakeStore(userId)
                Log.d(TAG, "AuthenticationViewModel | Store: $store")
                createStoreUseCase(getUrlFor("sales-store"), PostStoreRequest(key = storeKey, store = store)).collect { result ->
                    result.onSuccess { response ->
                        Log.d(TAG, "AuthenticationViewModel | signInWithFirebase: store created")
                        withContext(Dispatchers.Main) {
                            onSuccess(response.token)
                        }
                    }.onFailure { exception ->
                        Log.e(TAG, "AuthenticationViewModel | signInWithFirebase: store wasn't created ${exception.message}")
                        withContext(Dispatchers.Main) {
                            onFailure(exception)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "AuthenticationViewModel | Exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    onFailure(e)
                }
            }
        }
    }
}

fun getFakeStore(id: String): StoreDto {
    return StoreDto(
        id = id,
        name = "Maia Store",
        image = ImageDto(path = null, url = "", belongs = true),
        address = AddressDto(
            street = "...",
            city = "...",
            state = "...",
            zipCode = "...",
            country = "...",
            location = GeoPointDto(type = "Point", coordinates = listOf(-78.488751, -0.227497))
        ),
        phoneNumber = "0",
        emailAddress = "@",
        website = "www",
        description = "...",
        returnPolicy = "...",
        refundPolicy = "...",
        brands = emptyList(),
        status = StoreStatusDto(
            isActive = false,
            isVerified = false,
            isPromoted = false,
            isSuspended = false,
            isClosed = false,
            isPendingApproval = false,
            isUnderReview = false,
            isOutOfStock = false,
            isOnSale = false
        )
    )
}
