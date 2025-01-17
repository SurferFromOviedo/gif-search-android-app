package com.example.gifsearch.data

import com.example.gifsearch.data.network.GiphyApiService

/*
    Gifs repository in charge for getting the gifs from the API
 */

interface GifsRepository {
    suspend fun getGifs(
        query: String,
        offset: Int
    ): Gifs
    suspend fun getTrendingGifs(
        offset: Int
    ): Gifs
}

class NetworkGifsRepository(
    private val giphyApiService: GiphyApiService
): GifsRepository {
    override suspend fun getGifs(
        query: String,
        offset: Int
    ): Gifs = mapGifsWithColor(
        giphyApiService.getGifs(query = query, offset = offset)
    )
    override suspend fun getTrendingGifs(
        offset: Int
    ): Gifs = mapGifsWithColor(
        giphyApiService.getTrendingGifs(offset = offset)
    )

}

fun mapGifsWithColor(gifs: Gifs): Gifs {
    return Gifs(
        data = gifs.data.map { gif ->
            gif.copy(backgroundColor = getGiphyColor())
        }
    )
}

private fun getGiphyColor(): String {
    val randomNum = (0..4).random()
    return when (randomNum) {
        0 -> "#FFFF99"
        1 -> "#FF6666"
        2 -> "#00FF99"
        3 -> "#00CCFF"
        4 -> "#9933FF"
        else -> "000000"
    }
}