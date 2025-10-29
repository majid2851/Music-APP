package com.musicapk.presentation.splash

import android.app.Activity
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.musicapk.R
import com.musicapk.presentation.splash.components.SplashContent
import com.musicapk.util.PermissionUtil

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onGetStartedClick:()->Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val view = LocalView.current
    val context = LocalContext.current
    
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onGetStartedClick()
        }
    }
    
    SideEffect {
        hideDefaultToolbar(view)
    }
    
    SplashContent(
        uiState = uiState,
        onGetStartedClick = {
            // Mark first launch as complete
            viewModel.onEvent(SplashUiEvent.OnGetStartedClick)
            
            if (PermissionUtil.isMusicPermissionGranted(context)) {
                onGetStartedClick()
            } else {
                permissionLauncher.launch(PermissionUtil.getMusicPermission())
            }
        }
    )
}

private fun hideDefaultToolbar(view: View) {
    val window = (view.context as Activity).window
    val insetsController = WindowCompat.getInsetsController(window, view)

    insetsController.hide(WindowInsetsCompat.Type.systemBars())
    insetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
}



@Preview
@Composable
private fun SplashScreenPreview()
{
    SplashScreen(onGetStartedClick = {})
}







