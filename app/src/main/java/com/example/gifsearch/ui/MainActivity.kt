package com.example.gifsearch.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.gifsearch.ui.theme.GifSearchTheme


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels(
        factoryProducer = { MainViewModel.Factory }
    )
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isOnlineLiveData.observe(this){
            if(it){
                viewModel.manageNetworkStatus(true)
            }else{
                viewModel.manageNetworkStatus(false)
            }
        }
        enableEdgeToEdge()
        setContent {
            GifSearchTheme {
                val windowSize = calculateWindowSizeClass(this)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppRoot(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        windowWidth = windowSize.widthSizeClass
                    )
                }
            }
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewModel.scrollToTop()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            viewModel.scrollToTop()
        }
    }
}

