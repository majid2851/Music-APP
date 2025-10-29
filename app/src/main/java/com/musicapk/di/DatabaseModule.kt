package com.musicapk.di

import android.content.Context
import androidx.room.Room
import com.musicapk.data.local.MusicDatabase
import com.musicapk.data.local.dao.PlaylistDao
import com.musicapk.data.local.dao.SongDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideMusicDatabase(
        @ApplicationContext context: Context
    ): MusicDatabase {
        return Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            MusicDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    @Singleton
    fun provideSongDao(database: MusicDatabase): SongDao {
        return database.songDao()
    }
    
    @Provides
    @Singleton
    fun providePlaylistDao(database: MusicDatabase): PlaylistDao {
        return database.playlistDao()
    }
}

