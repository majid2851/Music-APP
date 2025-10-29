package com.musicapk.presentation.songs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.musicapk.presentation.songs.component.albums.AlbumsList
import com.musicapk.presentation.songs.component.artists.ArtistsList
import com.musicapk.presentation.songs.component.play_list.CreatePlaylistDialog
import com.musicapk.presentation.songs.component.favorites.FavoritesList
import com.musicapk.presentation.songs.component.MainTabs
import com.musicapk.presentation.songs.component.play_list.PlaylistsList
import com.musicapk.presentation.songs.component.SearchSongAndArtistAndPlayList
import com.musicapk.presentation.songs.component.SongsScreenWelcome
import com.musicapk.presentation.songs.model.MainTabEnum
import com.musicapk.ui.general_component.ExitDialog
import com.musicapk.ui.general_component.SongsList
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.styles.screenPaddings

@Composable
fun Songs(
    viewModel: SongsViewModel = hiltViewModel(),
    onPlayListClick: (String) -> Unit,
    onSongClick: () -> Unit,
    onExitApp: () -> Unit = {}
)
{
    val uiState by viewModel.uiState.collectAsState()
    val view = LocalView.current
    var showExitDialog by remember { mutableStateOf(false) }

    // Handle back press
    BackHandler {
        showExitDialog = true
    }

    SongsContent(
        uiState = uiState,
        onSearchQueryChange = { query ->
            viewModel.onEvent(SongsUiEvent.SearchSongs(query))
        },
        onSongClick = { song ->
            viewModel.onEvent(SongsUiEvent.PlaySong(song))
            onSongClick()
        },
        onPlayListClick = { playlistId ->
            onPlayListClick(playlistId)
        },
        onCreatePlaylistClick = {
            viewModel.onEvent(SongsUiEvent.ShowCreatePlaylistDialog)
        },
        onDismissDialog = {
            viewModel.onEvent(SongsUiEvent.HideCreatePlaylistDialog)
        },
        onCreatePlaylist = { name, description ->
            viewModel.onEvent(SongsUiEvent.CreatePlaylist(name, description))
        },
        onCategorySelected = { tab ->
            viewModel.onEvent(SongsUiEvent.SelectMainTab(tab as MainTabEnum))
        }
    )

    // Show exit dialog
    if (showExitDialog) {
        ExitDialog(
            onDismiss = {
                showExitDialog = false
            },
            onConfirm = {
                showExitDialog = false
                onExitApp()
            }
        )
    }
}

@Composable
private fun SongsContent(
    uiState: SongsUiState,
    onSearchQueryChange: (String) -> Unit,
    onPlayListClick: (String) -> Unit,
    onSongClick: (com.musicapk.domain.model.Song) -> Unit,
    onCreatePlaylistClick: () -> Unit,
    onDismissDialog: () -> Unit,
    onCreatePlaylist: (String, String?) -> Unit,
    onCategorySelected: (Any) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .gradientScreenBackground()
            .fillMaxSize()
            .screenPaddings()
    ) {
        SongsScreenWelcome()

        SearchSongAndArtistAndPlayList(
            searchQuery = uiState.searchQuery,
            onSearchQueryChange = onSearchQueryChange
        )

        MainTabs(
            selectedTab = uiState.selectedMainTab,
            onTabSelected = onCategorySelected as (MainTabEnum) -> Unit
        )

        when (uiState.selectedMainTab) {

            MainTabEnum.SONGS -> {
                SongsList(
                    songs = uiState.songs,
                    onSongClick = onSongClick
                )
            }

            MainTabEnum.FAVORITES -> {
                FavoritesList(
                    favoriteSongs = uiState.favoriteSongs,
                    onSongClick = onSongClick
                )
            }
            MainTabEnum.PLAYLISTS -> {
                PlaylistsList(
                    playlists = uiState.playlists,
                    onCreatePlaylistClick = onCreatePlaylistClick,
                    onPlaylistClick = { playlist ->
                        onPlayListClick(playlist.id)
                    },
                )
            }
//            MainTabEnum.ARTISTS -> {
//                ArtistsList(
//                    artists = uiState.artists,
//                    onArtistClick = { artist ->
//
//                    }
//                )
//            }
//
//            MainTabEnum.ALBUMS -> {
//                AlbumsList(
//                    albums = uiState.albums,
//                    onAlbumClick = { album ->
//
//                    }
//                )
//            }
        }
    }

    if (uiState.showCreatePlaylistDialog) {
        CreatePlaylistDialog(
            onDismiss = onDismissDialog,
            onCreate = onCreatePlaylist
        )
    }
}

@Preview(
    name = "Songs Screen - Light",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun SongsScreenPreview() {
    val sampleState = SongsUiState(
        songs = listOf(
            com.musicapk.domain.model.Song(
                id = "1",
                title = "Bye Bye",
                artist = "Marshmello, Juice WRLD",
                album = "Unknown",
                duration = 129000,
                artworkUri = null,
                audioUri = "",
                dateAdded = 0
            )
        )
    )
    MusicApkTheme(darkTheme = false, dynamicColor = false) {
        SongsContent(
            uiState = sampleState,
            onSearchQueryChange = {},
            onSongClick = {},
            onPlayListClick = {},
            onCreatePlaylistClick = {},
            onDismissDialog = {},
            onCreatePlaylist = { _, _ -> },
            onCategorySelected = {}
        )
    }
}

@Preview(
    name = "Songs Screen - Dark",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun SongsScreenDarkPreview() {
    val sampleState = SongsUiState(
        songs = listOf(
            com.musicapk.domain.model.Song(
                id = "1",
                title = "Bye Bye",
                artist = "Marshmello, Juice WRLD",
                album = "Unknown",
                duration = 129000,
                artworkUri = null,
                audioUri = "",
                dateAdded = 0
            )
        )
    )
    MusicApkTheme(darkTheme = true, dynamicColor = false) {
        SongsContent(
            uiState = sampleState,
            onSearchQueryChange = {},
            onPlayListClick = {},
            onSongClick = {},
            onCreatePlaylistClick = {},
            onDismissDialog = {},
            onCreatePlaylist = { _, _ -> },
            onCategorySelected = {}
        )
    }
}

