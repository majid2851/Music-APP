package com.musicapk.presentation.songs

import com.musicapk.presentation.base.UiEffect

sealed class  SongsUiEffect : UiEffect {
    data class ShowError(val message: String) : SongsUiEffect()
    data class ShowSnackbar(val message: String) : SongsUiEffect()
    data class NavigateToPlayer(val songId: String) : SongsUiEffect()
}

