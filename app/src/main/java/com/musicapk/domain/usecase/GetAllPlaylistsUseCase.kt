package com.musicapk.domain.usecase

import com.musicapk.domain.model.Playlist
import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPlaylistsUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(): Flow<Result<List<Playlist>>> {
        return musicRepository.getAllPlaylists()
    }
}







