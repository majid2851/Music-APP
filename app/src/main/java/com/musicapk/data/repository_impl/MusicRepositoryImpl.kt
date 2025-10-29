package com.musicapk.data.repository_impl

import com.musicapk.data.local.dao.PlaylistDao
import com.musicapk.data.local.dao.SongDao
import com.musicapk.data.local.datasource.MusicDataSource
import com.musicapk.data.local.entity.PlaylistEntity
import com.musicapk.data.local.entity.PlaylistSongCrossRef
import com.musicapk.data.mapper.toDomain
import com.musicapk.data.mapper.toDomainPlaylists
import com.musicapk.domain.model.Album
import com.musicapk.domain.model.Artist
import com.musicapk.domain.model.Playlist
import com.musicapk.domain.model.Song
import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val songDao: SongDao,
    private val playlistDao: PlaylistDao,
    private val musicDataSource: MusicDataSource
) : MusicRepository {
    
    override fun getAllSongs(): Flow<Result<List<Song>>> {
        return songDao.getAllSongs()
            .map<List<com.musicapk.data.local.entity.SongEntity>, Result<List<Song>>> { 
                Result.Success(it.toDomain()) 
            }
            .catch { emit(Result.Error(it)) }
    }
    
    override suspend fun getSongById(id: String): Result<Song> {
        return try {
            val song = songDao.getSongById(id)
            if (song != null) {
                Result.Success(song.toDomain())
            } else {
                Result.Error(Exception("Song not found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override fun searchSongs(query: String): Flow<Result<List<Song>>> {
        return songDao.searchSongs(query)
            .map<List<com.musicapk.data.local.entity.SongEntity>, Result<List<Song>>> { 
                Result.Success(it.toDomain()) 
            }
            .catch { emit(Result.Error(it)) }
    }
    
    override fun getFavoriteSongs(): Flow<Result<List<Song>>> {
        return songDao.getFavoriteSongs()
            .map<List<com.musicapk.data.local.entity.SongEntity>, Result<List<Song>>> { 
                Result.Success(it.toDomain()) 
            }
            .catch { emit(Result.Error(it)) }
    }
    
    override suspend fun toggleFavorite(songId: String): Result<Unit> {
        return try {
            songDao.toggleFavorite(songId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override fun getAllPlaylists(): Flow<Result<List<Playlist>>> {
        return playlistDao.getAllPlaylistsWithSongs()
            .map<List<com.musicapk.data.local.entity.PlaylistWithSongs>, Result<List<Playlist>>> { 
                Result.Success(it.toDomainPlaylists()) 
            }
            .catch { emit(Result.Error(it)) }
    }
    
    override suspend fun createPlaylist(name: String, description: String?): Result<Playlist> {
        return try {
            val currentTime = System.currentTimeMillis()
            val playlist = PlaylistEntity(
                id = UUID.randomUUID().toString(),
                name = name,
                description = description,
                createdAt = currentTime,
                updatedAt = currentTime
            )
            playlistDao.insertPlaylist(playlist)
            
            Result.Success(
                Playlist(
                    id = playlist.id,
                    name = playlist.name,
                    description = playlist.description,
                    songs = emptyList(),
                    createdAt = playlist.createdAt,
                    updatedAt = playlist.updatedAt
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun addSongToPlaylist(playlistId: String, songId: String): Result<Unit> {
        return try {
            val crossRef = PlaylistSongCrossRef(
                playlistId = playlistId,
                songId = songId,
                position = 0
            )
            playlistDao.insertPlaylistSongCrossRef(crossRef)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun removeSongFromPlaylist(playlistId: String, songId: String): Result<Unit> {
        return try {
            playlistDao.removeSongFromPlaylist(playlistId, songId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun deletePlaylist(playlistId: String): Result<Unit> {
        return try {
            playlistDao.deletePlaylistById(playlistId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override fun getAllAlbums(): Flow<Result<List<Album>>> = flow {
        try {
            songDao.getAllSongs().collect { songEntities ->
                val songs = songEntities.toDomain()
                
                // Group songs by album
                val albums = songs
                    .filter { it.album.isNotBlank() }
                    .groupBy { it.album }
                    .map { (albumName, albumSongs) ->
                        Album(
                            id = albumName.hashCode().toString(),
                            name = albumName,
                            artist = albumSongs.firstOrNull()?.artist ?: "Unknown Artist",
                            artworkUri = albumSongs.firstOrNull()?.artworkUri,
                            songCount = albumSongs.size,
                            songs = albumSongs
                        )
                    }
                    .sortedBy { it.name }
                
                emit(Result.Success(albums))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getAllArtists(): Flow<Result<List<Artist>>> = flow {
        try {
            songDao.getAllSongs().collect { songEntities ->
                val songs = songEntities.toDomain()
                
                // Group songs by artist
                val artists = songs
                    .filter { it.artist.isNotBlank() }
                    .groupBy { it.artist }
                    .map { (artistName, artistSongs) ->
                        val albums = artistSongs
                            .map { it.album }
                            .filter { it.isNotBlank() }
                            .distinct()
                            .size
                        
                        Artist(
                            id = artistName.hashCode().toString(),
                            name = artistName,
                            artworkUri = artistSongs.firstOrNull()?.artworkUri,
                            songCount = artistSongs.size,
                            albumCount = albums,
                            songs = artistSongs
                        )
                    }
                    .sortedBy { it.name }
                
                emit(Result.Success(artists))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun syncMusicFromDevice(): Result<Unit> {
        return try {
            val songs = musicDataSource.loadMusicFromDevice()
            
            // Step 1: Insert new songs (IGNORE strategy skips existing ones)
            songDao.insertSongs(songs)
            
            // Step 2: Update metadata for existing songs (preserves isFavorite flag)
            songs.forEach { song ->
                songDao.updateSongMetadata(
                    id = song.id,
                    title = song.title,
                    artist = song.artist,
                    album = song.album,
                    duration = song.duration,
                    artworkUri = song.artworkUri,
                    audioUri = song.audioUri,
                    dateAdded = song.dateAdded
                )
            }
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override fun getSongsByPlaylistId(playlistId: String): Flow<Result<List<Song>>> = flow {
        try {
            val playlistWithSongs = playlistDao.getPlaylistWithSongs(playlistId)
            if (playlistWithSongs != null) {
                val songs = playlistWithSongs.songs.toDomain()
                emit(Result.Success(songs))
            } else {
                emit(Result.Success(emptyList()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}

