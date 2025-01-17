package com.example.gifsearch

import android.app.Application
import com.example.gifsearch.data.AppContainer
import com.example.gifsearch.data.DefaultAppContainer

/*
    Application class that initializes the app container
 */

class GifApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}