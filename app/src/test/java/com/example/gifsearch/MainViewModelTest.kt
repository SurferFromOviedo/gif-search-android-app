package com.example.gifsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.testing.asSnapshot
import com.example.gifsearch.fake.FakeConnectivityRepository
import com.example.gifsearch.fake.FakeDataSource
import com.example.gifsearch.fake.FakeNetworkGifsRepository
import com.example.gifsearch.ui.MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/*
    Tests for the MainViewModel
 */

@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun mainViewModel_getGifs_verifyGifs() =
        runTest{
            val viewModel = MainViewModel(
                gifsRepository = FakeNetworkGifsRepository(),
                connectivityRepository = FakeConnectivityRepository()
            )
            val pagingGifsList = viewModel.gifs.asSnapshot()
            assertEquals(FakeDataSource.gifs.data[0].title, pagingGifsList[0].title)
        }

    @Test
    fun mainViewModel_queryInput_verifyQuery() {
        val viewModel = MainViewModel(
            gifsRepository = FakeNetworkGifsRepository(),
            connectivityRepository = FakeConnectivityRepository()
        )
        val query = "test"
        viewModel.updateQuery(query)
        assertEquals(query, viewModel.query.value)
    }

    @Test
    fun mainViewModel_scrollToTop_verifyState() {
        val viewModel = MainViewModel(
            gifsRepository = FakeNetworkGifsRepository(),
            connectivityRepository = FakeConnectivityRepository()
        )
        viewModel.scrollToTop()
        assertEquals(viewModel.scrollState.firstVisibleItemIndex, 0)
    }

    @Test
    fun mainViewModel_showGif_verifyGif() {
        runTest{
            val viewModel = MainViewModel(
                gifsRepository = FakeNetworkGifsRepository(),
                connectivityRepository = FakeConnectivityRepository()
            )
            val pagingGifsList = viewModel.gifs.asSnapshot()
            viewModel.showGif(pagingGifsList[0])
            assertEquals(viewModel.selectedGif, pagingGifsList[0])
        }
    }
    
    @Test
    fun mainViewModel_manageNetworkStatus_verifyStatus() {
        runTest{
            val viewModel = MainViewModel(
                gifsRepository = FakeNetworkGifsRepository(),
                connectivityRepository = FakeConnectivityRepository()
            )
            viewModel.manageNetworkStatus(false)
            assertEquals(viewModel.isOnline, false)
        }
    }

    @Test
    fun mainViewModel_setIsRetryPending_verifyPending() {
        runTest{
            val viewModel = MainViewModel(
                gifsRepository = FakeNetworkGifsRepository(),
                connectivityRepository = FakeConnectivityRepository()
            )
            viewModel.setIsRetryPending(true)
            assertEquals(viewModel.isRetryPending, true)
        }
    }
}