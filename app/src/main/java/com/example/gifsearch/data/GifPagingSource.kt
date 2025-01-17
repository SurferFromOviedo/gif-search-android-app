package com.example.gifsearch.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

/*
    Class in charge for pagination. Uses the GifsRepository to get the gifs. If the query is empty,
    it will get the trending gifs, otherwise it will search the gifs with the query
 */

class GifPagingSource(
    private val gifsRepository: GifsRepository,
    private val query: String
): PagingSource<Int, Gif>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gif> {
        return try {
            val pageNumber = params.key ?: 0
            val response = if (query.isEmpty()) gifsRepository.getTrendingGifs(offset = pageNumber * 50)
                else gifsRepository.getGifs(query = query, offset = pageNumber * 50)
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.data.isNotEmpty()) pageNumber + 1 else null
            LoadResult.Page(
                data = response.data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Gif>): Int? {
        return null
    }
}