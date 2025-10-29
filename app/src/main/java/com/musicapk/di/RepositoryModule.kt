package com.musicapk.di

import com.musicapk.data.repository_impl.MusicRepositoryImpl
import com.musicapk.data.repository_impl.PlayerRepositoryImpl
import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.repository.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindMusicRepository(
        musicRepositoryImpl: MusicRepositoryImpl
    ): MusicRepository
    
    @Binds
    @Singleton
    abstract fun bindPlayerRepository(
        playerRepositoryImpl: PlayerRepositoryImpl
    ): PlayerRepository
}

