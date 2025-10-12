package com.musicapk.presentation.splash.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.musicapk.R
import com.musicapk.presentation.splash.SplashUiState

@Composable
fun SplashContent(
    uiState: SplashUiState,
) {
    Box(
        modifier= Modifier.fillMaxSize()
    )
    {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "",
        )



    }
}