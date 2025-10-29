package com.musicapk.data.repository_impl

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.musicapk.domain.model.PlayerState
import com.musicapk.domain.model.RepeatMode
import com.musicapk.domain.model.Song
import com.musicapk.domain.repository.PlayerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PlayerRepository {
    
    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())
    private var positionUpdateJob: Job? = null
    
    private val _playerState = MutableStateFlow(PlayerState())
    
    init {
        setupPlayerListener()
        startPositionUpdater()
    }
    
    private fun setupPlayerListener() {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_ENDED -> {
                        coroutineScope.launch {
                            skipToNext()
                        }
                    }
                    Player.STATE_READY -> {
                        _playerState.value = _playerState.value.copy(
                            isLoading = false
                        )
                    }
                    Player.STATE_BUFFERING -> {
                        _playerState.value = _playerState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
            
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playerState.value = _playerState.value.copy(
                    isPlaying = isPlaying
                )
            }
        })
    }
    
    private fun startPositionUpdater() {
        positionUpdateJob?.cancel()
        positionUpdateJob = coroutineScope.launch {
            while (isActive) {
                if (exoPlayer.isPlaying) {
                    _playerState.value = _playerState.value.copy(
                        currentPosition = exoPlayer.currentPosition
                    )
                }
                delay(100) // Update every 100ms for smooth progress bar
            }
        }
    }
    
    override fun observePlayerState(): Flow<PlayerState> {
        return _playerState.asStateFlow()
    }
    
    override suspend fun playSong(song: Song) {
        // Check if the same song is already playing
        if (_playerState.value.currentSong?.id == song.id) {
            // Just resume if paused, don't restart
            if (!exoPlayer.isPlaying) {
                exoPlayer.play()
                _playerState.value = _playerState.value.copy(isPlaying = true)
            }
            return
        }
        
        // Different song - load and play it
        val mediaItem = MediaItem.fromUri(song.audioUri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
        
        _playerState.value = _playerState.value.copy(
            currentSong = song,
            isPlaying = true,
            currentPosition = 0L,
            playlist = listOf(song)
        )
    }
    
    override suspend fun playPlaylist(songs: List<Song>, startIndex: Int) {
        if (songs.isEmpty() || startIndex !in songs.indices) return
        
        val selectedSong = songs[startIndex]
        
        // Check if the same song is already playing
        if (_playerState.value.currentSong?.id == selectedSong.id) {
            // Just resume if paused, don't restart
            if (!exoPlayer.isPlaying) {
                exoPlayer.play()
                _playerState.value = _playerState.value.copy(isPlaying = true)
            }
            return
        }
        
        // Different song - load and play playlist
        // Create media items for all songs
        val mediaItems = songs.map { song ->
            MediaItem.fromUri(song.audioUri)
        }
        
        exoPlayer.setMediaItems(mediaItems, startIndex, 0L)
        exoPlayer.prepare()
        exoPlayer.play()
        
        _playerState.value = _playerState.value.copy(
            currentSong = songs[startIndex],
            isPlaying = true,
            currentPosition = 0L,
            playlist = songs
        )
    }
    
    override suspend fun pause() {
        exoPlayer.pause()
        _playerState.value = _playerState.value.copy(isPlaying = false)
    }
    
    override suspend fun resume() {
        exoPlayer.play()
        _playerState.value = _playerState.value.copy(isPlaying = true)
    }
    
    override suspend fun skipToNext() {
        val currentState = _playerState.value
        val currentPlaylist = currentState.playlist
        val currentSong = currentState.currentSong
        val repeatMode = currentState.repeatMode
        
        if (currentPlaylist.isEmpty() || currentSong == null) return
        
        val currentIndex = currentPlaylist.indexOf(currentSong)
        if (currentIndex == -1) return
        
        val nextIndex = when {
            // If shuffle is enabled, pick a random song
            currentState.shuffleEnabled -> {
                val availableIndices = currentPlaylist.indices.filter { it != currentIndex }
                availableIndices.randomOrNull() ?: currentIndex
            }
            // If at the end of playlist
            currentIndex >= currentPlaylist.size - 1 -> {
                when (repeatMode) {
                    RepeatMode.ALL -> 0 // Go to first song
                    RepeatMode.ONE -> {
                        exoPlayer.seekTo(0)
                        return
                    }
                    RepeatMode.OFF -> {
                        exoPlayer.pause()
                        return
                    }
                }
            }
            // Normal case: go to next
            else -> currentIndex + 1
        }
        
        if (nextIndex != currentIndex) {
            exoPlayer.seekTo(nextIndex, 0L)
            exoPlayer.play()
            
            _playerState.value = currentState.copy(
                currentSong = currentPlaylist[nextIndex],
                currentPosition = 0L
            )
        }
    }
    
    override suspend fun skipToPrevious() {
        val currentState = _playerState.value
        val currentPlaylist = currentState.playlist
        val currentSong = currentState.currentSong
        val currentPosition = exoPlayer.currentPosition
        
        if (currentPlaylist.isEmpty() || currentSong == null) return
        
        // If more than 3 seconds into the song, restart it instead of going to previous
        if (currentPosition > 3000) {
            exoPlayer.seekTo(0)
            _playerState.value = currentState.copy(currentPosition = 0L)
            return
        }
        
        val currentIndex = currentPlaylist.indexOf(currentSong)
        if (currentIndex == -1) return
        
        val previousIndex = when {
            // If shuffle is enabled, pick a random song
            currentState.shuffleEnabled -> {
                val availableIndices = currentPlaylist.indices.filter { it != currentIndex }
                availableIndices.randomOrNull() ?: currentIndex
            }
            // If at the beginning of playlist
            currentIndex == 0 -> {
                when (currentState.repeatMode) {
                    RepeatMode.ALL -> currentPlaylist.size - 1 // Go to last song
                    RepeatMode.ONE -> {
                        exoPlayer.seekTo(0)
                        return
                    }
                    RepeatMode.OFF -> return // Don't skip
                }
            }
            // Normal case: go to previous
            else -> currentIndex - 1
        }
        
        if (previousIndex != currentIndex) {
            exoPlayer.seekTo(previousIndex, 0L)
            exoPlayer.play()
            
            _playerState.value = currentState.copy(
                currentSong = currentPlaylist[previousIndex],
                currentPosition = 0L
            )
        }
    }
    
    override suspend fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
        _playerState.value = _playerState.value.copy(currentPosition = position)
    }
    
    override suspend fun setRepeatMode(mode: RepeatMode) {
        _playerState.value = _playerState.value.copy(repeatMode = mode)
    }
    
    override suspend fun toggleShuffle() {
        _playerState.value = _playerState.value.copy(
            shuffleEnabled = !_playerState.value.shuffleEnabled
        )
    }
    
    override suspend fun release() {
        positionUpdateJob?.cancel()
        exoPlayer.release()
        _playerState.value = PlayerState()
    }
}












