package com.musicapk.domain.repository

import com.musicapk.domain.model.Album
import com.musicapk.domain.model.Artist
import com.musicapk.domain.model.Playlist
import com.musicapk.domain.model.Song
import com.musicapk.domain.util.Result
import kotlinx.coroutines.flow.Flow


interface MusicRepository {

    fun getAllSongs(): Flow<Result<List<Song>>>

    suspend fun getSongById(id: String): Result<Song>

    fun searchSongs(query: String): Flow<Result<List<Song>>>

    fun getFavoriteSongs(): Flow<Result<List<Song>>>

    suspend fun toggleFavorite(songId: String): Result<Unit>

    fun getAllPlaylists(): Flow<Result<List<Playlist>>>

    suspend fun createPlaylist(name: String, description: String?): Result<Playlist>

    suspend fun addSongToPlaylist(playlistId: String, songId: String): Result<Unit>

    suspend fun removeSongFromPlaylist(playlistId: String, songId: String): Result<Unit>

    suspend fun deletePlaylist(playlistId: String): Result<Unit>

    fun getAllAlbums(): Flow<Result<List<Album>>>

    fun getAllArtists(): Flow<Result<List<Artist>>>

    suspend fun syncMusicFromDevice(): Result<Unit>
    
    fun getSongsByPlaylistId(playlistId: String): Flow<Result<List<Song>>>
}













