package com.musicapk.domain.usecase

import com.musicapk.domain.model.Song
import com.musicapk.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayPlaylistUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(songs: List<Song>, startIndex: Int = 0) {
        playerRepository.playPlaylist(songs, startIndex)
    }
}









