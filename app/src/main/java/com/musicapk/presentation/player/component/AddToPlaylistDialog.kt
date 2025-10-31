package com.musicapk.presentation.player.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.domain.model.Playlist
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.Strings

@Composable
fun AddToPlaylistDialog(
    playlists: List<Playlist>,
    onDismiss: () -> Unit,
    onPlaylistSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = Strings.selectPlaylist,
                fontWeight = FontWeight.Bold,
                color = AppColors.White
            )
        },
        text = {
            if (playlists.isEmpty()) {
                Text(
                    text = Strings.noPlaylistsAvailable,
                    color = AppColors.LightGray,
                    modifier = Modifier.padding(vertical = Dimens.paddingMedium)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(playlists) { playlist ->
                        PlaylistItem(
                            playlist = playlist,
                            onClick = {
                                onPlaylistSelected(playlist.id)
                                onDismiss()
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = Strings.cancel,
                    color = AppColors.White
                )
            }
        },
        containerColor = AppColors.GradientBlue1,
        titleContentColor = AppColors.White,
        textContentColor = AppColors.White,
        shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
    )
}

@Composable
private fun PlaylistItem(
    playlist: Playlist,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = Dimens.paddingMedium, horizontal = Dimens.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.PlaylistPlay,
            contentDescription = playlist.name,
            tint = AppColors.White,
            modifier = Modifier.padding(end = Dimens.paddingMedium)
        )
        
        Column {
            Text(
                text = playlist.name,
                fontWeight = FontWeight.Bold,
                fontSize = FontSizes.medium,
                color = AppColors.White
            )
            
            Text(
                text = "${playlist.songs.size} songs",
                fontSize = FontSizes.small,
                color = AppColors.LightGray
            )
        }
    }
}

@Preview
@Composable
private fun AddToPlaylistDialogPreview() {
    val samplePlaylists = listOf(
        Playlist(
            id = "1",
            name = "Favorites",
            description = "My favorite tracks",
            songs = emptyList(),
            createdAt = 0L,
            updatedAt = 0L
        ),
        Playlist(
            id = "2",
            name = "Workout Mix",
            description = null,
            songs = emptyList(),
            createdAt = 0L,
            updatedAt = 0L
        )
    )
    
    AddToPlaylistDialog(
        playlists = samplePlaylists,
        onDismiss = {},
        onPlaylistSelected = {}
    )
}

