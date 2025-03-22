package com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.service

import android.content.ContentValues
import android.util.Log
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.Group
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.GroupServiceable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GroupService @Inject constructor(private val httpClient: HttpClient) : GroupServiceable {

    override fun addGroup(url: String, group: Group): Flow<Result<Group>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(group)
                }
                when (response.status) {
                    HttpStatusCode.Companion.Created -> {
                        val responseBody = response.body<Group>()
                        Log.d(ContentValues.TAG, "DataService | Product added: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    HttpStatusCode.Companion.Found -> {
                        val responseBody = response.body<Group>()
                        Log.d(ContentValues.TAG, "DataService | Product already exists: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    HttpStatusCode.Companion.NotFound -> {
                        Log.d(ContentValues.TAG, "DataService | Error with status code: ${response.status}")
                        emit(Result.failure(RuntimeException("Error occurred: Server responded with status code ${response.status}")))
                    }

                    else -> {
                        Log.d(ContentValues.TAG, "DataService | Unexpected response status: ${response.status}")
                        emit(Result.failure(Exception("Unexpected response status: ${response.status}")))
                    }
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "DataService | Failed to post product: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }

    override fun getGroups(url: String): Flow<Result<List<Group>>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.get(url)
                Log.d(ContentValues.TAG, "ProductService | We have products: ${response.body<List<Group>>()}")
                emit(Result.success(response.body()))
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "ProductService | We have no products: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }
}