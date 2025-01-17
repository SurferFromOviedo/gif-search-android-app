package com.example.gifsearch

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.example.gifsearch.data.network.ConnectivityRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/*
    Tests for the ConnectivityRepository
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ConnectivityRepositoryTest {

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var network: Network

    @Captor
    private lateinit var networkCallbackCaptor: ArgumentCaptor<ConnectivityManager.NetworkCallback>

    private lateinit var connectivityRepository: ConnectivityRepository

    @Before
    fun setup() {
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        connectivityRepository = ConnectivityRepository(context)

        verify(connectivityManager).registerDefaultNetworkCallback(networkCallbackCaptor.capture())
    }

    @Test
    fun connectivityRepository_isConnected_returnsTrue() = runTest {
        networkCallbackCaptor.value.onAvailable(network)
        val isConnected = connectivityRepository.isConnected.first()
        assertEquals(true, isConnected)
    }

    @Test
    fun connectivityRepository_onLost_returnsFalse() = runTest {
        networkCallbackCaptor.value.onLost(network)
        val isConnected = connectivityRepository.isConnected.first()
        assertEquals(false, isConnected)
    }

}