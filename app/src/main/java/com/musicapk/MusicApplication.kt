package com.musicapk

import android.app.Application
import androidx.media3.ui.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MusicApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }   
        
        Timber.d("MusicApplication initialized")
    }
}

