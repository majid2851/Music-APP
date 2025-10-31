package com.musicapk.presentation.songs

import com.musicapk.domain.model.Song
import com.musicapk.presentation.base.UiEvent
import com.musicapk.presentation.songs.model.MainTabEnum
import com.musicapk.presentation.songs.model.SongCategoryEnum

sealed class SongsUiEvent : UiEvent {
    object LoadSongs : SongsUiEvent()
    data class SearchSongs(val query: String) : SongsUiEvent()
    data class SelectSong(val song: Song) : SongsUiEvent()
    data class PlaySong(val song: Song) : SongsUiEvent()
    data class ToggleFavorite(val songId: String) : SongsUiEvent()
    object ClearError : SongsUiEvent()
    
    // Playlist events
    object LoadPlaylists : SongsUiEvent()
    object ShowCreatePlaylistDialog : SongsUiEvent()
    object HideCreatePlaylistDialog : SongsUiEvent()
    data class CreatePlaylist(val name: String, val description: String?) : SongsUiEvent()
    data class DeletePlaylist(val playlistId: String) : SongsUiEvent()
    
    // Tab events
    data class SelectMainTab(val tab: MainTabEnum) : SongsUiEvent()
    
    // Category events (for Songs tab)
    data class SelectCategory(val category: SongCategoryEnum) : SongsUiEvent()
}

