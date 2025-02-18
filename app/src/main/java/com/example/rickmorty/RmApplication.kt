package com.example.rickmorty

import android.app.Application
import com.example.rickmorty.data.AppContainer
import com.example.rickmorty.data.DefaultAppContainer

class RmApplication: Application() {
    // App container instance used by the rest of the classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}