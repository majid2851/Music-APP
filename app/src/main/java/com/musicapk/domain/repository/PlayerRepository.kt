package com.musicapk.domain.repository

import com.musicapk.domain.model.PlayerState
import com.musicapk.domain.model.RepeatMode
import com.musicapk.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    

    fun observePlayerState(): Flow<PlayerState>

    suspend fun playSong(song: Song)

    suspend fun playPlaylist(songs: List<Song>, startIndex: Int = 0)

    suspend fun pause()

    suspend fun resume()

    suspend fun skipToNext()

    suspend fun skipToPrevious()

    suspend fun seekTo(position: Long)

    suspend fun setRepeatMode(mode: RepeatMode)

    suspend fun toggleShuffle()

    suspend fun release()
}

























