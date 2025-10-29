package com.musicapk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.musicapk.data.local.dao.PlaylistDao
import com.musicapk.data.local.dao.SongDao
import com.musicapk.data.local.entity.PlaylistEntity
import com.musicapk.data.local.entity.PlaylistSongCrossRef
import com.musicapk.data.local.entity.SongEntity

@Database(
    entities = [
        SongEntity::class,
        PlaylistEntity::class,
        PlaylistSongCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao
    
    companion object {
        const val DATABASE_NAME = "music_database"
    }
}

