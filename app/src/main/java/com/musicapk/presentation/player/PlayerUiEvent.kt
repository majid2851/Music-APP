package com.musicapk.presentation.player

import com.musicapk.domain.model.RepeatMode
import com.musicapk.presentation.base.UiEvent

sealed class PlayerUiEvent : UiEvent {
    object PlayPause : PlayerUiEvent()
    object SkipNext : PlayerUiEvent()
    object SkipPrevious : PlayerUiEvent()
    data class SeekTo(val position: Long) : PlayerUiEvent()
    data class SetRepeatMode(val mode: RepeatMode) : PlayerUiEvent()
    object ToggleShuffle : PlayerUiEvent()
    object ToggleFavorite : PlayerUiEvent()
    
    // More actions
    object ShowAddToPlaylistDialog : PlayerUiEvent()
    object HideAddToPlaylistDialog : PlayerUiEvent()
    data class AddToPlaylist(val playlistId: String) : PlayerUiEvent()
    object ShareSong : PlayerUiEvent()
    object ShowSongDetails : PlayerUiEvent()
    object HideSongDetails : PlayerUiEvent()
}

