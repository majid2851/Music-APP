package com.musicapk.domain.usecase

import com.musicapk.domain.model.Song
import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongsByPlaylistIdUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(playlistId: String): Flow<Result<List<Song>>> {
        return musicRepository.getSongsByPlaylistId(playlistId)
    }
}

