package com.musicapk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.musicapk.data.preferences.PreferencesManager
import com.musicapk.navigation.NavGraph
import com.musicapk.navigation.Screen
import com.musicapk.ui.theme.MusicApkTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var preferencesManager: PreferencesManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

