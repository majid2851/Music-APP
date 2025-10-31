package com.musicapk

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.compose.rememberNavController
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.musicapk.data.preferences.PreferencesManager
import com.musicapk.domain.usecase.SyncMusicFromDeviceUseCase
import com.musicapk.navigation.NavGraph
import com.musicapk.navigation.Screen
import com.musicapk.service.MusicService
import com.musicapk.ui.theme.MusicApkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var preferencesManager: PreferencesManager
    
    @Inject
    lateinit var syncMusicFromDeviceUseCase: SyncMusicFromDeviceUseCase
    
    private var mediaControllerFuture: ListenableFuture<MediaController>? = null
    private var mediaController: MediaController? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Start the music service
        startMusicService()
        
        setContent {
            MusicApkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MusicApp(preferencesManager = preferencesManager)
                }
            }
        }
    }
    
    override fun onStart() {
        super.onStart()
        initializeMediaController()
    }
    
    override fun onResume() {
        super.onResume()
        // Sync music from device storage every time the app comes to foreground
        // This ensures new songs added to device storage are immediately available
        syncMusicFromDevice()
    }
    
    override fun onStop() {
        releaseMediaController()
        super.onStop()
    }
    
    /**
     * Starts the MusicService for background playback
     */
    private fun startMusicService() {
        val intent = Intent(this, MusicService::class.java)
        startService(intent)
    }
    
    /**
     * Syncs music from device storage to update the database
     * with any new songs or changes
     */
    private fun syncMusicFromDevice() {
        lifecycleScope.launch {
            syncMusicFromDeviceUseCase()
        }
    }
    
    /**
     * Initializes the MediaController to communicate with MusicService
     */
    private fun initializeMediaController() {
        val sessionToken = SessionToken(this, ComponentName(this, MusicService::class.java))
        mediaControllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        
        mediaControllerFuture?.addListener(
            {
                mediaController = mediaControllerFuture?.get()
            },
            MoreExecutors.directExecutor()
        )
    }
    
    /**
     * Releases the MediaController
     */
    private fun releaseMediaController() {
        mediaController?.release()
        mediaController = null
        mediaControllerFuture?.let {
            MediaController.releaseFuture(it)
        }
        mediaControllerFuture = null
    }
}

@Composable
fun MusicApp(preferencesManager: PreferencesManager) {
    val navController = rememberNavController()
    
    // Determine start destination based on whether it's the first launch
    val startDestination = if (preferencesManager.isFirstLaunch()) {
        Screen.Splash.route
    } else {
        Screen.Songs.route
    }
    
    NavGraph(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    )
}

