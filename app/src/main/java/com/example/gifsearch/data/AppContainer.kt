package com.example.gifsearch.data

import android.content.Context
import com.example.gifsearch.data.network.ConnectivityRepository
import com.example.gifsearch.data.network.GiphyApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/*
    App container that contains all dependencies for the app
 */

interface AppContainer {
    val gifsRepository: GifsRepository
    val connectivityRepository: ConnectivityRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl =
        "https://api.giphy.com/v1/gifs/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: GiphyApiService by lazy {
        retrofit.create(GiphyApiService::class.java)
    }

    override val gifsRepository: GifsRepository by lazy {
        NetworkGifsRepository(retrofitService)
    }

    override val connectivityRepository: ConnectivityRepository by lazy {
        ConnectivityRepository(context = context)
    }
}