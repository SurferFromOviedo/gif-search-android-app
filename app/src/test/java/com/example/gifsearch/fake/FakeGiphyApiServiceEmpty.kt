package com.example.gifsearch.fake

import com.example.gifsearch.data.Gifs
import com.example.gifsearch.data.network.GiphyApiService

/*
    Fake GiphyApiService that returns empty response
 */

class FakeGiphyApiServiceEmpty: GiphyApiService {
    override suspend fun getGifs(apiKey: String, query: String, limit: Int, offset: Int): Gifs {
        return FakeDataSource.empty
    }

    override suspend fun getTrendingGifs(apiKey: String, limit: Int, offset: Int): Gifs {
        return FakeDataSource.empty
    }

}