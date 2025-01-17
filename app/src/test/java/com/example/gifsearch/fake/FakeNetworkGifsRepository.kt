package com.example.gifsearch.fake

import com.example.gifsearch.data.Gifs
import com.example.gifsearch.data.GifsRepository

/*
    Fake GifsRepository
 */

class FakeNetworkGifsRepository: GifsRepository {
    override suspend fun getGifs(query: String, offset: Int): Gifs {
        return FakeDataSource.gifs
    }
    override suspend fun getTrendingGifs(offset: Int): Gifs {
        return FakeDataSource.gifs
    }

}