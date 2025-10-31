package com.musicapk.domain.usecase

import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.util.Result
import javax.inject.Inject

class SyncMusicFromDeviceUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return musicRepository.syncMusicFromDevice()
    }
}













