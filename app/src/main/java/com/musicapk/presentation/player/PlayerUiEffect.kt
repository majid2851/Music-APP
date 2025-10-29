package com.musicapk.presentation.player

import android.content.Intent
import com.musicapk.presentation.base.UiEffect

sealed class PlayerUiEffect : UiEffect {
    data class ShowError(val message: String) : PlayerUiEffect()
    data class ShowSnackbar(val message: String) : PlayerUiEffect()
    object Close : PlayerUiEffect()
    data class ShareSong(val shareIntent: Intent) : PlayerUiEffect()
}

