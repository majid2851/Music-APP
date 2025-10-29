package com.musicapk.presentation.splash

import com.musicapk.presentation.base.UiEvent

sealed class SplashUiEvent : UiEvent {
    object Initialize : SplashUiEvent()
    object CheckPermissions : SplashUiEvent()
    object RetryInitialization : SplashUiEvent()
    object OnGetStartedClick : SplashUiEvent()
}















