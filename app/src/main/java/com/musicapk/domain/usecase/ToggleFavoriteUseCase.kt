package com.musicapk.domain.usecase

import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.util.Result
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(songId: String): Result<Unit> {
        return musicRepository.toggleFavorite(songId)
    }
}

