package com.musicapk.domain.model


data class PlayerState(
    val currentSong: Song? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val repeatMode: RepeatMode = RepeatMode.OFF,
    val shuffleEnabled: Boolean = false,
    val playlist: List<Song> = emptyList(),
    val isLoading: Boolean = false
)

enum class RepeatMode {
    OFF,
    ONE,
    ALL
}


