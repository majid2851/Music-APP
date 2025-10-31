package com.musicapk.domain.usecase

import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.util.Result
import javax.inject.Inject

class AddSongToPlaylistUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(playlistId: String, songId: String): Result<Unit> {
        return musicRepository.addSongToPlaylist(playlistId, songId)
    }
}









