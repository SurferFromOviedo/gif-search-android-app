package com.example.gifsearch

import com.example.gifsearch.data.NetworkGifsRepository
import com.example.gifsearch.fake.FakeDataSource
import com.example.gifsearch.fake.FakeGiphyApiService
import com.example.gifsearch.fake.FakeGiphyApiServiceEmpty
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/*
    Tests for GifsRepository
 */

class NetworkGifsRepositoryTest {
    private lateinit var repository: NetworkGifsRepository
    private lateinit var emptyRepository: NetworkGifsRepository

    @Before
    fun setUp() {
        repository = NetworkGifsRepository(
            giphyApiService = FakeGiphyApiService()
        )
        emptyRepository = NetworkGifsRepository(
            giphyApiService = FakeGiphyApiServiceEmpty()
        )
    }

    @Test
    fun networkGifsRepository_getGifs_verifyGif() =
        runTest {
            assertEquals(
                FakeDataSource.gifs.data[0].title,
                repository.getGifs(query = "test", offset = 0).data[0].title)
        }

    @Test
    fun networkGifsRepository_getTrendingGifs_verifyGif() =
        runTest {
            assertEquals(
                FakeDataSource.gifs.data[1].title,
                repository.getTrendingGifs(offset = 0).data[1].title)
        }

    @Test
    fun networkGifsRepository_getNoGifs_verifyNull() =
        runTest {
            assertEquals(FakeDataSource.empty, emptyRepository.getGifs(query = "test", offset = 0))
        }
}