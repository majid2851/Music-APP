package com.musicapk.presentation.splash

import com.musicapk.presentation.base.UiEffect

sealed class SplashUiEffect : UiEffect {
    object NavigateToMain : SplashUiEffect()
    object NavigateToPermissions : SplashUiEffect()
    data class ShowError(val message: String) : SplashUiEffect()
}



















