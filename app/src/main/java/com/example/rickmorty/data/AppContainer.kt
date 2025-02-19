package com.example.rickmorty.data

import android.content.Context
import com.example.rickmorty.data.AppDatabase.Companion.getDatabase
import com.example.rickmorty.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Dependency Injection container at the application level
 */
interface AppContainer {
    val repository: RmRepository
}

/**
 * Implementation for Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://rickandmortyapi.com/api"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val database = getDatabase(context)
    private val characterDao = database.characterDao()

    // OkHttp client with logging interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Moshi instance with Kotlin adapter factory
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    // Retrofit instance with Moshi converter
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    // Retrofit service for making API calls
    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // Repository implementation
    override val repository: RmRepository by lazy {
        DefaultRmRepository(retrofitService, characterDao)
    }
}