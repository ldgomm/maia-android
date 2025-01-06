package com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.service

import android.content.ContentValues.TAG
import android.util.Log
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.SearchServiceable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchService @Inject constructor(private val httpClient: HttpClient) : SearchServiceable {

    override fun getListOfData(
        endpoint: String,
        token: String
    ): Flow<Result<List<ProductDto>>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.get(endpoint) {
                    header(HttpHeaders.Authorization, "Bearer $token")
                }
                Log.d(TAG, "SearchService | We have products: ${response.body<List<ProductDto>>()}")
                emit(Result.success(response.body()))
            } catch (e: Exception) {
                Log.d(TAG, "SearchService | We have no products: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }
}
