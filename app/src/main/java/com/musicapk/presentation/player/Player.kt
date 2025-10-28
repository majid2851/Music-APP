package com.musicapk.presentation.player

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.musicapk.domain.model.Song
import com.musicapk.presentation.player.component.AddToPlaylistDialog
import com.musicapk.presentation.player.component.BackAndMoreIcons
import com.musicapk.presentation.player.component.SongDetailsDialog
import com.musicapk.presentation.player.component.SongImage
import com.musicapk.presentation.player.component.SongInfo
import com.musicapk.presentation.player.component.SongOperators
import com.musicapk.presentation.player.component.SongProgressBar
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.Strings
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.styles.screenPaddings
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Player(
    viewModel: PlayerViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    // Handle effects
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is PlayerUiEffect.ShareSong -> {
                    context.startActivity(effect.shareIntent)
                }
                is PlayerUiEffect.ShowError -> {
                    // TODO: Show error toast/snackbar
                }
                is PlayerUiEffect.ShowSnackbar -> {
                    // TODO: Show snackbar
                }
                is PlayerUiEffect.Close -> {
                    onBackClick()
                }
            }
        }
    }
    
    PlayerContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick
    )
}

@Composable
private fun PlayerContent(
    uiState: PlayerUiState,
    onEvent: (PlayerUiEvent) -> Unit,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .gradientScreenBackground()
            .fillMaxSize()
            .screenPaddings()
    ) {
        BackAndMoreIcons(
            onBackClick = onBackClick,
            onAddToPlaylist = {
                onEvent(PlayerUiEvent.ShowAddToPlaylistDialog)
            },
            onShare = {
                onEvent(PlayerUiEvent.ShareSong)
            },
            onViewDetails = {
                onEvent(PlayerUiEvent.ShowSongDetails)
            }
        )

        SongImage(
            artworkUri = uiState.currentSong?.artworkUri,
            modifier = Modifier
                .padding(
                    top = Dimens.paddingMedium,
                    bottom = Dimens.paddingLarge,
                )
        )

        SongInfo(
            title = uiState.currentSong?.title ?: Strings.unknownTitle,
            artist = uiState.currentSong?.artist ?: Strings.unknownArtist,
            isFavorite = uiState.currentSong?.isFavorite ?: false,
            onFavoriteClick = { onEvent(PlayerUiEvent.ToggleFavorite) }
        )

        SongProgressBar(
            currentPosition = uiState.currentPosition,
            duration = uiState.duration,
            onFinishSong = {
                onEvent(PlayerUiEvent.SkipNext)
            },
            onSeek = {
                position -> onEvent(PlayerUiEvent.SeekTo(position))
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        SongOperators(
            isPlaying = uiState.isPlaying,
            isShuffleEnabled = uiState.isShuffleEnabled,
            repeatMode = uiState.repeatMode,
            onPlayPauseClick = { onEvent(PlayerUiEvent.PlayPause) },
            onSkipPreviousClick = { onEvent(PlayerUiEvent.SkipPrevious) },
            onSkipNextClick = { onEvent(PlayerUiEvent.SkipNext) },
            onShuffleClick = { onEvent(PlayerUiEvent.ToggleShuffle) },
            onRepeatClick = {
                val nextMode = when (uiState.repeatMode) {
                    com.musicapk.domain.model.RepeatMode.OFF -> com.musicapk.domain.model.RepeatMode.ALL
                    com.musicapk.domain.model.RepeatMode.ALL -> com.musicapk.domain.model.RepeatMode.ONE
                    com.musicapk.domain.model.RepeatMode.ONE -> com.musicapk.domain.model.RepeatMode.OFF
                }
                onEvent(PlayerUiEvent.SetRepeatMode(nextMode))
            },
            modifier = Modifier
                .padding(bottom = Dimens.paddingXXL)
        )
    }

    if (uiState.showAddToPlaylistDialog) {
        AddToPlaylistDialog(
            playlists = uiState.availablePlaylists,
            onDismiss = { onEvent(PlayerUiEvent.HideAddToPlaylistDialog) },
            onPlaylistSelected = { playlistId ->
                onEvent(PlayerUiEvent.AddToPlaylist(playlistId))
            }
        )
    }
    if (uiState.showSongDetailsDialog && uiState.currentSong != null) {
        SongDetailsDialog(
            song = uiState.currentSong!!,
            onDismiss = { onEvent(PlayerUiEvent.HideSongDetails) }
        )
    }
}


@Preview
@Composable
private fun PlayerPreview() {
    val sampleState = PlayerUiState(
        currentSong = Song(
            id = "1",
            title = "Bye Bye",
            artist = "Marshmello, Juice WRLD",
            album = "Unknown",
            duration = 129000,
            artworkUri = null,
            audioUri = "",
            dateAdded = 0,
            isFavorite = true
        ),
        isPlaying = true,
        currentPosition = 45000,
        duration = 129000
    )
    
    PlayerContent(
        uiState = sampleState,
        onEvent = {}
    )
}
