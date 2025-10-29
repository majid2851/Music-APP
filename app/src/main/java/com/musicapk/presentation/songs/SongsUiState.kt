package com.musicapk.presentation.songs

import com.musicapk.domain.model.Album
import com.musicapk.domain.model.Artist
import com.musicapk.domain.model.Playlist
import com.musicapk.domain.model.Song
import com.musicapk.presentation.base.UiState
import com.musicapk.presentation.songs.model.MainTabEnum
import com.musicapk.presentation.songs.model.SongCategoryEnum

data class SongsUiState(
    val songs: List<Song> = emptyList(),
    val favoriteSongs: List<Song> = emptyList(),
    val playlists: List<Playlist> = emptyList(),
    val albums: List<Album> = emptyList(),
    val artists: List<Artist> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedSong: Song? = null,
    val showCreatePlaylistDialog: Boolean = false,
    val selectedCategory: SongCategoryEnum = SongCategoryEnum.ALL_MUSIC,
    val selectedMainTab: MainTabEnum = MainTabEnum.SONGS
) : UiState

