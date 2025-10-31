package com.musicapk.domain.usecase

import com.musicapk.domain.model.Song
import com.musicapk.domain.repository.PlayerRepository
import javax.inject.Inject

class PlaySongUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(song: Song) {
        playerRepository.playSong(song)
    }
}

























