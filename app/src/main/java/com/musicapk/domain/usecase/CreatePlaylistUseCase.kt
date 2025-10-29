package com.musicapk.domain.usecase

import com.musicapk.domain.model.Playlist
import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.util.Result
import javax.inject.Inject

class CreatePlaylistUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(name: String, description: String? = null): Result<Playlist> {
        return musicRepository.createPlaylist(name, description)
    }
}







