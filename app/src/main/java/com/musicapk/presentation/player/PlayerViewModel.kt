package com.musicapk.presentation.player

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.musicapk.domain.repository.PlayerRepository
import com.musicapk.domain.usecase.AddSongToPlaylistUseCase
import com.musicapk.domain.usecase.GetAllPlaylistsUseCase
import com.musicapk.domain.usecase.ToggleFavoriteUseCase
import com.musicapk.domain.util.Result
import com.musicapk.presentation.base.BaseViewModel
import com.musicapk.ui.theme.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getAllPlaylistsUseCase: GetAllPlaylistsUseCase,
    private val addSongToPlaylistUseCase: AddSongToPlaylistUseCase
) : BaseViewModel<PlayerUiState, PlayerUiEvent, PlayerUiEffect>(
    initialState = PlayerUiState()
) {

    init {
        observePlayerState()
        loadPlaylists()
    }

    private fun observePlayerState() {
        viewModelScope.launch {
            playerRepository.observePlayerState().collect { playerState ->
                setState {
                    copy(
                        currentSong = playerState.currentSong,
                        isPlaying = playerState.isPlaying,
                        currentPosition = playerState.currentPosition,
                        duration = playerState.currentSong?.duration ?: 0L,
                        repeatMode = playerState.repeatMode,
                        isShuffleEnabled = playerState.shuffleEnabled,
                        playlist = playerState.playlist,
                        isLoading = playerState.isLoading
                    )
                }
            }
        }
    }

    override fun onEvent(event: PlayerUiEvent) {
        when (event) {
            is PlayerUiEvent.PlayPause -> togglePlayPause()
            is PlayerUiEvent.SkipNext -> skipToNext()
            is PlayerUiEvent.SkipPrevious -> skipToPrevious()
            is PlayerUiEvent.SeekTo -> seekTo(event.position)
            is PlayerUiEvent.SetRepeatMode -> setRepeatMode(event.mode)
            is PlayerUiEvent.ToggleShuffle -> toggleShuffle()
            is PlayerUiEvent.ToggleFavorite -> toggleFavorite()
            is PlayerUiEvent.ShowAddToPlaylistDialog -> showAddToPlaylistDialog()
            is PlayerUiEvent.HideAddToPlaylistDialog -> hideAddToPlaylistDialog()
            is PlayerUiEvent.AddToPlaylist -> addToPlaylist(event.playlistId)
            is PlayerUiEvent.ShareSong -> shareSong()
            is PlayerUiEvent.ShowSongDetails -> showSongDetails()
            is PlayerUiEvent.HideSongDetails -> hideSongDetails()
        }
    }

    private fun togglePlayPause() {
        viewModelScope.launch {
            try {
                if (currentState.isPlaying) {
                    playerRepository.pause()
                } else {
                    playerRepository.resume()
                }
            } catch (e: Exception) {
                sendEffect(
                    PlayerUiEffect.ShowError("${Strings.errorPlayPause}: ${e.message}")
                )
            }
        }
    }

    private fun skipToNext() {
        viewModelScope.launch {
            try {
                playerRepository.skipToNext()
            } catch (e: Exception) {
                sendEffect(
                    PlayerUiEffect.ShowError("${Strings.errorSkipNext}: ${e.message}")
                )
            }
        }
    }

    private fun skipToPrevious() {
        viewModelScope.launch {
            try {
                playerRepository.skipToPrevious()
            } catch (e: Exception) {
                sendEffect(
                    PlayerUiEffect.ShowError("${Strings.errorSkipPrevious}: ${e.message}")
                )
            }
        }
    }

    private fun seekTo(position: Long) {
        viewModelScope.launch {
            try {
                playerRepository.seekTo(position)
            } catch (e: Exception) {
                sendEffect(
                    PlayerUiEffect.ShowError("${Strings.errorSeek}: ${e.message}")
                )
            }
        }
    }

    private fun setRepeatMode(mode: com.musicapk.domain.model.RepeatMode) {
        viewModelScope.launch {
            try {
                playerRepository.setRepeatMode(mode)
            } catch (e: Exception) {
                sendEffect(
                    PlayerUiEffect.ShowError("${Strings.errorRepeatMode}: ${e.message}")
                )
            }
        }
    }

    private fun toggleShuffle() {
        viewModelScope.launch {
            try {
                playerRepository.toggleShuffle()
            } catch (e: Exception) {
                sendEffect(
                    PlayerUiEffect.ShowError("${Strings.errorShuffle}: ${e.message}")
                )
            }
        }
    }

    private fun toggleFavorite() {
        val currentSong = currentState.currentSong ?: return
        
        viewModelScope.launch {
            // Optimistic UI update
            val previousFavoriteState = currentSong.isFavorite
            setState {
                copy(
                    currentSong = currentSong.copy(isFavorite = !previousFavoriteState)
                )
            }

            when (val result = toggleFavoriteUseCase(currentSong.id)) {
                is Result.Success -> {
                    sendEffect(PlayerUiEffect.ShowSnackbar(Strings.favoriteUpdated))
                }
                is Result.Error -> {
                    // Revert the optimistic update on error
                    setState {
                        copy(
                            currentSong = currentSong.copy(isFavorite = previousFavoriteState)
                        )
                    }
                    sendEffect(
                        PlayerUiEffect.ShowError("${Strings.errorFavorite}: ${result.exception.message}")
                    )
                }
                is Result.Loading -> {}
            }
        }
    }

    private fun loadPlaylists() {
        viewModelScope.launch {
            getAllPlaylistsUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        setState { copy(availablePlaylists = result.data) }
                    }
                    is Result.Error -> {
                        // Silently fail - playlists are optional feature
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }

    private fun showAddToPlaylistDialog() {
        setState { copy(showAddToPlaylistDialog = true) }
    }

    private fun hideAddToPlaylistDialog() {
        setState { copy(showAddToPlaylistDialog = false) }
    }

    private fun addToPlaylist(playlistId: String) {
        val currentSong = currentState.currentSong ?: return
        
        viewModelScope.launch {
            when (val result = addSongToPlaylistUseCase(playlistId, currentSong.id)) {
                is Result.Success -> {
                    sendEffect(PlayerUiEffect.ShowSnackbar(Strings.songAddedToPlaylist))
                    setState { copy(showAddToPlaylistDialog = false) }
                }
                is Result.Error -> {
                    sendEffect(
                        PlayerUiEffect.ShowError("Failed to add song: ${result.exception.message}")
                    )
                }
                is Result.Loading -> {}
            }
        }
    }

    private fun shareSong() {
        val currentSong = currentState.currentSong ?: return
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "audio/*"
            
            if (currentSong.audioUri.isNotEmpty()) {
                putExtra(Intent.EXTRA_STREAM, currentSong.audioUri.toUri())
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            putExtra(Intent.EXTRA_SUBJECT, currentSong.title)
            putExtra(
                Intent.EXTRA_TEXT,
                "${currentSong.title} - ${currentSong.artist}"
            )
        }
        
        val chooserIntent = Intent.createChooser(shareIntent, "Share ${currentSong.title}")
        sendEffect(PlayerUiEffect.ShareSong(chooserIntent))
    }

    private fun showSongDetails() {
        setState { copy(showSongDetailsDialog = true) }
    }

    private fun hideSongDetails() {
        setState { copy(showSongDetailsDialog = false) }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            try {
                playerRepository.release()
            } catch (e: Exception) {
                // Ignore errors on cleanup
            }
        }
    }
}
