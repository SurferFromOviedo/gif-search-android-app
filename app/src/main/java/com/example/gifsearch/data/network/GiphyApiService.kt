package com.example.gifsearch.data.network

import com.example.gifsearch.data.Gifs
import retrofit2.http.GET
import retrofit2.http.Query

/*
    Api service for Giphy
 */

private const val API_KEY = "YOUR_KEY"

interface GiphyApiService {
    @GET("search")
    suspend fun getGifs(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("q") query: String = "cats",
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Gifs

    @GET("trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Gifs
}