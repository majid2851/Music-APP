package com.musicapk.presentation.play_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.presentation.play_list.component.SelectedPlayList
import com.musicapk.ui.general_component.SongsList
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.styles.screenPaddings
import com.musicapk.presentation.songs.SongsViewModel
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.musicapk.domain.model.Song

@Composable
fun MusicPlayList(
    onSongClick: (Song) -> Unit,
    playListId: String,
    viewModel: SongsViewModel = hiltViewModel()
) {
    val playlistSongs = viewModel.getPlaylistSongs(playListId)
        .collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .gradientScreenBackground()
            .fillMaxSize()
    ) {
        SelectedPlayList(playlistId = playListId)

        SongsList(
            songs = playlistSongs.value,
            modifier = Modifier.screenPaddings(),
            onSongClick = { song ->
                viewModel.playSongFromPlaylist(song, playlistSongs.value)
                onSongClick(song)
            },
            onRemoveSong = { song ->
                viewModel.removeSongFromPlaylist(playListId, song.id)
            }
        )
    }
}

@Preview(
    name = "Music Overview Screen",
    showBackground = true
)
@Composable
private fun MusicOverviewPreview() {
    MusicPlayList(
        onSongClick = {}, // Sample onClick for preview purposes
        playListId = "sample_playlist_id"  // Sample playlist ID for preview
    )
}