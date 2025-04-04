package com.premierdarkcoffee.sales.maia.root.di

import android.content.Context
import androidx.room.Room
import com.premierdarkcoffee.sales.maia.root.feature.authentication.data.service.AuthenticationService
import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.serviceable.AuthenticationServiceable
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.database.MainDatabase
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.repository.MessageRepository
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.repositoriable.MessageRepositoriable
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.service.GroupService
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.service.ProductService
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.service.SearchService
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.GroupServiceable
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.ProductServiceable
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.SearchServiceable
import com.premierdarkcoffee.sales.maia.root.feature.settings.data.remote.service.StoreService
import com.premierdarkcoffee.sales.maia.root.feature.settings.domain.serviceable.StoreServiceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyInjection {

    @Singleton
    @Provides
    fun provideMainDatabase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(context, MainDatabase::class.java, "main_database").build()
    }

    @Singleton
    @Provides
    fun provideMessageRepositoriable(database: MainDatabase): MessageRepositoriable {
        return MessageRepository(database)
    }

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    allowStructuredMapKeys = true
                    prettyPrint = true
                })
            }
            install(Logging) { level = LogLevel.ALL }
        }
    }

    @Singleton
    @Provides
    fun provideAuthenticationServiceable(httpClient: HttpClient): AuthenticationServiceable {
        return AuthenticationService(httpClient)
    }

    @Singleton
    @Provides
    fun provideProductServiceable(httpClient: HttpClient): ProductServiceable {
        return ProductService(httpClient)
    }

    @Singleton
    @Provides
    fun provideSearchServiceable(httpClient: HttpClient): SearchServiceable {
        return SearchService(httpClient)
    }

    @Singleton
    @Provides
    fun provideStoreServiceable(httpClient: HttpClient): StoreServiceable {
        return StoreService(httpClient)
    }

    @Singleton
    @Provides
    fun provideDataServiceable(httpClient: HttpClient): GroupServiceable {
        return GroupService(httpClient)
    }
}
