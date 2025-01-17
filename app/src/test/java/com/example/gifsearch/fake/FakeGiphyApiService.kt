package com.example.gifsearch.fake

import com.example.gifsearch.data.Gifs
import com.example.gifsearch.data.network.GiphyApiService

/*
    Fake GiphyApiService
 */

class FakeGiphyApiService: GiphyApiService {
    override suspend fun getGifs(apiKey: String, query: String, limit: Int, offset: Int): Gifs {
        return FakeDataSource.gifs
    }

    override suspend fun getTrendingGifs(apiKey: String, limit: Int, offset: Int): Gifs {
        return FakeDataSource.gifs
    }

}