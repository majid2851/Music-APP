package com.musicapk.domain.usecase

import com.musicapk.domain.model.Song
import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


class SearchSongsUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(query: String): Flow<Result<List<Song>>> {
        if (query.isBlank()) {
            return flowOf(Result.Success(emptyList()))
        }
        return musicRepository.searchSongs(query)
    }
}

























