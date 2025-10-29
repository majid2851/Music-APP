package com.musicapk.presentation.player

import com.musicapk.domain.model.Playlist
import com.musicapk.domain.model.RepeatMode
import com.musicapk.domain.model.Song
import com.musicapk.presentation.base.UiState

data class PlayerUiState(
    val currentSong: Song? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val repeatMode: RepeatMode = RepeatMode.OFF,
    val isShuffleEnabled: Boolean = false,
    val playlist: List<Song> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAddToPlaylistDialog: Boolean = false,
    val showSongDetailsDialog: Boolean = false,
    val availablePlaylists: List<Playlist> = emptyList()
) : UiState

