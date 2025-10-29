package com.musicapk.data.local.dao

import androidx.room.*
import com.musicapk.data.local.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    
    @Query("SELECT * FROM songs ORDER BY title ASC")
    fun getAllSongs(): Flow<List<SongEntity>>
    
    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun getSongById(id: String): SongEntity?
    
    @Query("SELECT * FROM songs WHERE title LIKE '%' || :query || '%' OR artist LIKE '%' || :query || '%' OR album LIKE '%' || :query || '%'")
    fun searchSongs(query: String): Flow<List<SongEntity>>
    
    @Query("SELECT * FROM songs WHERE isFavorite = 1 ORDER BY title ASC")
    fun getFavoriteSongs(): Flow<List<SongEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: SongEntity)
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSongs(songs: List<SongEntity>)
    
    @Update
    suspend fun updateSong(song: SongEntity)
    
    @Delete
    suspend fun deleteSong(song: SongEntity)
    
    @Query("UPDATE songs SET isFavorite = NOT isFavorite WHERE id = :songId")
    suspend fun toggleFavorite(songId: String)
    
    @Query("""
        UPDATE songs 
        SET title = :title,
            artist = :artist,
            album = :album,
            duration = :duration,
            artworkUri = :artworkUri,
            audioUri = :audioUri,
            dateAdded = :dateAdded
        WHERE id = :id
    """)
    suspend fun updateSongMetadata(
        id: String,
        title: String,
        artist: String,
        album: String,
        duration: Long,
        artworkUri: String?,
        audioUri: String,
        dateAdded: Long
    )
}

