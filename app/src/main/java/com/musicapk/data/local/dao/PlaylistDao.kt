package com.musicapk.data.local.dao

import androidx.room.*
import com.musicapk.data.local.entity.PlaylistEntity
import com.musicapk.data.local.entity.PlaylistSongCrossRef
import com.musicapk.data.local.entity.PlaylistWithSongs
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    
    @Transaction
    @Query("SELECT * FROM playlists ORDER BY name ASC")
    fun getAllPlaylistsWithSongs(): Flow<List<PlaylistWithSongs>>
    
    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    suspend fun getPlaylistWithSongs(playlistId: String): PlaylistWithSongs?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long
    
    @Update
    suspend fun updatePlaylist(playlist: PlaylistEntity)
    
    @Delete
    suspend fun deletePlaylist(playlist: PlaylistEntity)
    
    @Query("DELETE FROM playlists WHERE id = :playlistId")
    suspend fun deletePlaylistById(playlistId: String)
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlaylistSongCrossRef(crossRef: PlaylistSongCrossRef)
    
    @Delete
    suspend fun deletePlaylistSongCrossRef(crossRef: PlaylistSongCrossRef)
    
    @Query("DELETE FROM playlist_song_cross_ref WHERE playlistId = :playlistId AND songId = :songId")
    suspend fun removeSongFromPlaylist(playlistId: String, songId: String)
}

