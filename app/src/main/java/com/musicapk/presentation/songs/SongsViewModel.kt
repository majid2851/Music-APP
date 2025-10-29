package com.musicapk.presentation.songs

import androidx.lifecycle.viewModelScope
import com.musicapk.domain.usecase.GetAllSongsUseCase
import com.musicapk.domain.usecase.PlaySongUseCase
import com.musicapk.domain.usecase.SearchSongsUseCase
import com.musicapk.domain.usecase.SyncMusicFromDeviceUseCase
import com.musicapk.domain.usecase.ToggleFavoriteUseCase
import com.musicapk.domain.util.Result
import com.musicapk.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val searchSongsUseCase: SearchSongsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val playSongUseCase: PlaySongUseCase,
    private val syncMusicFromDeviceUseCase: SyncMusicFromDeviceUseCase,
    private val playPlaylistUseCase: com.musicapk.domain.usecase.PlayPlaylistUseCase,
    private val getAllPlaylistsUseCase: com.musicapk.domain.usecase.GetAllPlaylistsUseCase,
    private val getFavoriteSongsUseCase: com.musicapk.domain.usecase.GetFavoriteSongsUseCase,
    private val createPlaylistUseCase: com.musicapk.domain.usecase.CreatePlaylistUseCase,
    private val getAllAlbumsUseCase: com.musicapk.domain.usecase.GetAllAlbumsUseCase,
    private val getAllArtistsUseCase: com.musicapk.domain.usecase.GetAllArtistsUseCase,
    private val getSongsByPlaylistIdUseCase: com.musicapk.domain.usecase.GetSongsByPlaylistIdUseCase
) : BaseViewModel<SongsUiState, SongsUiEvent, SongsUiEffect>(
    initialState = SongsUiState()
) {
    
    private var searchJob: Job? = null
    
    init {
        syncMusicFromDevice()
        onEvent(SongsUiEvent.LoadSongs)
        loadPlaylists()
        loadFavoriteSongs()
        loadAlbums()
        loadArtists()
    }
    
    override fun onEvent(event: SongsUiEvent) {
        when (event) {
            is SongsUiEvent.LoadSongs -> loadSongs()
            is SongsUiEvent.SearchSongs -> searchSongs(event.query)
            is SongsUiEvent.SelectSong -> selectSong(event.song)
            is SongsUiEvent.PlaySong -> playSong(event.song)
            is SongsUiEvent.ToggleFavorite -> toggleFavorite(event.songId)
            is SongsUiEvent.ClearError -> clearError()
            is SongsUiEvent.LoadPlaylists -> loadPlaylists()
            is SongsUiEvent.ShowCreatePlaylistDialog -> setState { copy(showCreatePlaylistDialog = true) }
            is SongsUiEvent.HideCreatePlaylistDialog -> setState { copy(showCreatePlaylistDialog = false) }
            is SongsUiEvent.CreatePlaylist -> createPlaylist(event.name, event.description)
            is SongsUiEvent.SelectMainTab -> setState { copy(selectedMainTab = event.tab) }
            is SongsUiEvent.SelectCategory -> setState { copy(selectedCategory = event.category) }
        }
    }
    
    private fun loadSongs() {
        viewModelScope.launch {
            getAllSongsUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState { copy(isLoading = true, error = null) }
                    }
                    is Result.Success -> {
                        setState { 
                            copy(
                                songs = result.data,
                                isLoading = false,
                                error = null
                            ) 
                        }
                    }
                    is Result.Error -> {
                        setState { 
                            copy(
                                isLoading = false,
                                error = result.exception.message
                            ) 
                        }
                        sendEffect(
                            SongsUiEffect.ShowError(
                                result.exception.message ?: "Unknown error occurred"
                            )
                        )
                    }
                }
            }
        }
    }
    
    private fun searchSongs(query: String) {
        searchJob?.cancel()
        
        setState { copy(searchQuery = query) }
        
        if (query.isBlank()) {
            loadSongs()
            return
        }
        
        searchJob = viewModelScope.launch {
            searchSongsUseCase(query).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState { copy(isLoading = true) }
                    }
                    is Result.Success -> {
                        setState { 
                            copy(
                                songs = result.data,
                                isLoading = false,
                                error = null
                            ) 
                        }
                    }
                    is Result.Error -> {
                        setState { 
                            copy(
                                isLoading = false,
                                error = result.exception.message
                            ) 
                        }
                    }
                }
            }
        }
    }
    
    private fun selectSong(song: com.musicapk.domain.model.Song) {
        setState { copy(selectedSong = song) }
    }
    
    private fun playSong(song: com.musicapk.domain.model.Song) {
        viewModelScope.launch {
            try {
                // Play the song with the entire current song list as playlist
                val currentSongs = currentState.songs
                val songIndex = currentSongs.indexOf(song)
                
                if (songIndex != -1 && currentSongs.isNotEmpty()) {
                    // Play as playlist so user can skip between songs
                    playPlaylistUseCase(currentSongs, songIndex)
                } else {
                    // Fallback to single song if not found in list
                    playSongUseCase(song)
                }
                
                sendEffect(SongsUiEffect.NavigateToPlayer(song.id))
            } catch (e: Exception) {
                sendEffect(
                    SongsUiEffect.ShowError("Failed to play song: ${e.message}")
                )
            }
        }
    }
    
    private fun toggleFavorite(songId: String) {
        viewModelScope.launch {
            when (val result = toggleFavoriteUseCase(songId)) {
                is Result.Success -> {
                    sendEffect(SongsUiEffect.ShowSnackbar("Favorite updated"))
                }
                is Result.Error -> {
                    sendEffect(
                        SongsUiEffect.ShowError("Failed to update favorite: ${result.exception.message}")
                    )
                }
                is Result.Loading -> { /* No-op */ }
            }
        }
    }
    
    private fun clearError() {
        setState { copy(error = null) }
    }
    
    private fun syncMusicFromDevice() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            when (syncMusicFromDeviceUseCase()) {
                is Result.Success -> {
                    setState { copy(isLoading = false) }
                }
                is Result.Error -> {
                    setState { copy(isLoading = false) }
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
                        setState { copy(playlists = result.data) }
                    }
                    is Result.Error -> {
                        sendEffect(
                            SongsUiEffect.ShowError("Failed to load playlists: ${result.exception.message}")
                        )
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }
    
    private fun loadFavoriteSongs() {
        viewModelScope.launch {
            getFavoriteSongsUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        setState { copy(favoriteSongs = result.data) }
                    }
                    is Result.Error -> {
                        sendEffect(
                            SongsUiEffect.ShowError("Failed to load favorite songs: ${result.exception.message}")
                        )
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }
    
    private fun createPlaylist(name: String, description: String?) {
        viewModelScope.launch {
            when (val result = createPlaylistUseCase(name, description)) {
                is Result.Success -> {
                    setState { copy(showCreatePlaylistDialog = false) }
                    sendEffect(SongsUiEffect.ShowSnackbar("Playlist created successfully"))
                    loadPlaylists() // Reload playlists
                }
                is Result.Error -> {
                    sendEffect(
                        SongsUiEffect.ShowError("Failed to create playlist: ${result.exception.message}")
                    )
                }
                is Result.Loading -> {}
            }
        }
    }
    
    private fun loadAlbums() {
        viewModelScope.launch {
            getAllAlbumsUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        setState { copy(albums = result.data) }
                    }
                    is Result.Error -> {
                        sendEffect(
                            SongsUiEffect.ShowError("Failed to load albums: ${result.exception.message}")
                        )
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }
    
    private fun loadArtists() {
        viewModelScope.launch {
            getAllArtistsUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        setState { copy(artists = result.data) }
                    }
                    is Result.Error -> {
                        sendEffect(
                            SongsUiEffect.ShowError("Failed to load artists: ${result.exception.message}")
                        )
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }

    fun getPlaylistSongs(playlistId: String): Flow<List<com.musicapk.domain.model.Song>> {
        return kotlinx.coroutines.flow.flow {
            getSongsByPlaylistIdUseCase(playlistId).collect { result ->
                when (result) {
                    is Result.Success -> emit(result.data)
                    is Result.Error -> emit(emptyList())
                    is Result.Loading -> { /* No-op */ }
                }
            }
        }
    }
    
    fun playSongFromPlaylist(song: com.musicapk.domain.model.Song, playlistSongs: List<com.musicapk.domain.model.Song>) {
        viewModelScope.launch {
            try {
                val songIndex = playlistSongs.indexOf(song)
                
                if (songIndex != -1 && playlistSongs.isNotEmpty()) {
                    // Play as playlist so user can skip between songs
                    playPlaylistUseCase(playlistSongs, songIndex)
                } else {
                    // Fallback to single song if not found in list
                    playSongUseCase(song)
                }
            } catch (e: Exception) {
                sendEffect(
                    SongsUiEffect.ShowError("Failed to play song: ${e.message}")
                )
            }
        }
    }
}

