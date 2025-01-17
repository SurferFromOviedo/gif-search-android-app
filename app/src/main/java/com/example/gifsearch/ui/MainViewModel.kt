package com.example.gifsearch.ui

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.gifsearch.GifApplication
import com.example.gifsearch.data.Gif
import com.example.gifsearch.data.GifPagingSource
import com.example.gifsearch.data.GifsRepository
import com.example.gifsearch.data.network.ConnectivityRepositoryInterface
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*
    ViewModel with Factory to pass values to it
 */

@OptIn(FlowPreview::class)
class MainViewModel(
    private val gifsRepository: GifsRepository,
    private val connectivityRepository: ConnectivityRepositoryInterface
): ViewModel() {

    var selectedGif by mutableStateOf<Gif?>(null)
        private set

    var isOnline by mutableStateOf(true)
        private set

    var isRetryPending by mutableStateOf(false)
        private set

    var scrollState = LazyStaggeredGridState()
        private set

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun scrollToTop(){
        viewModelScope.launch {
            scrollState.scrollToItem(0)
        }
    }

    val isOnlineLiveData = connectivityRepository.isConnected.asLiveData()

    private var pagingSource: GifPagingSource? = null
        get(){
            if (field == null || field?.invalid == true){
                field = GifPagingSource(
                    gifsRepository = gifsRepository,
                    query = query.value
                )
            }
            return field
        }

    val gifs = Pager(
        config = PagingConfig(
            pageSize = 50,
            prefetchDistance = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            pagingSource!!
        }
    ).flow.cachedIn(viewModelScope)

    private fun initializeSearch(){
        viewModelScope.launch {
            query.debounce(500L).collectLatest{
                scrollState.scrollToItem(0)
                pagingSource?.invalidate()
            }
        }
    }

    fun updateQuery(query: String){
        if (_query.value == query) return
        _query.update { query }
        initializeSearch()
    }

    fun showGif(gif: Gif?){
        selectedGif = gif
    }

    fun manageNetworkStatus(isOnline: Boolean){
        this@MainViewModel.isOnline = isOnline
    }

    fun setIsRetryPending(isRetryPending: Boolean){
        this@MainViewModel.isRetryPending = isRetryPending
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GifApplication)
                val gifsRepository = application.container.gifsRepository
                val connectivityRepository = application.container.connectivityRepository
                MainViewModel(
                    gifsRepository = gifsRepository,
                    connectivityRepository = connectivityRepository
                )
            }
        }
    }
}