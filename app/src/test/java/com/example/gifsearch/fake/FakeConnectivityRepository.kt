package com.example.gifsearch.fake

import com.example.gifsearch.data.network.ConnectivityRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/*
    Fake ConnectivityRepository
 */

class FakeConnectivityRepository() : ConnectivityRepositoryInterface {
    private val _isConnected = MutableStateFlow(true)
    override val isConnected: Flow<Boolean> = _isConnected

    fun setConnectivityStatus(isConnected: Boolean) {
        _isConnected.value = isConnected
    }
}