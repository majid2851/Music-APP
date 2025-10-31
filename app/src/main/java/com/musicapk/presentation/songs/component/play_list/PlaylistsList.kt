package com.musicapk.presentation.songs.component.play_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicapk.domain.model.Playlist
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.Strings
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun PlaylistsList(
    playlists: List<Playlist>,
    onPlaylistClick: (Playlist) -> Unit,
    onCreatePlaylistClick: () -> Unit,
    onDeletePlaylist: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
    ) {
        item {
            CreatePlaylistListItem(
                onClick = onCreatePlaylistClick
            )
        }

        items(playlists) { playlist ->
            PlaylistListItem(
                playlist = playlist,
                onClick = { onPlaylistClick(playlist) },
                onDelete = { onDeletePlaylist(playlist.id) }
            )
        }

    }
}

@Composable
private fun CreatePlaylistListItem(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = Dimens.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Playlist cover
        Box(
            modifier = Modifier
                .size(Dimens.playListItemHeight)
                .clip(RoundedCornerShape(Dimens.cornerRadiusSmall))
                .background(AppColors.DarkGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = Strings.createPlaylist,
                modifier = Modifier.size(32.dp),
                tint = AppColors.White
            )
        }

        // Playlist info
        Column(
            modifier = Modifier
                .padding(start = Dimens.paddingMedium)
                .weight(1f)
        ) {
            Text(
                text = Strings.createPlaylist,
                color = AppColors.White,
                fontSize = FontSizes.medium,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Composable
private fun PlaylistListItem(
    playlist: Playlist,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = Dimens.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Playlist cover
        Box(
            modifier = Modifier
                .size(Dimens.playListItemHeight)
                .clip(RoundedCornerShape(Dimens.cornerRadiusSmall))
                .background(AppColors.DarkGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlaylistPlay,
                contentDescription = playlist.name,
                modifier = Modifier.size(32.dp),
                tint = AppColors.White.copy(alpha = 0.7f)
            )
        }

        Column(
            modifier = Modifier
                .padding(start = Dimens.paddingMedium)
                .weight(1f)
        ) {
            Text(
                text = playlist.name,
                color = AppColors.White,
                fontSize = FontSizes.medium,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
            
            Text(
                text = "Playlist · ${playlist.songs.size} songs",
                color = AppColors.LightGray,
                fontSize = FontSizes.small,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(top = Dimens.paddingExtraSmall)
            )
        }
        
        // Delete button
        IconButton(
            onClick = { showDeleteDialog = true }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete playlist",
                tint = AppColors.White.copy(alpha = 0.6f)
            )
        }
    }
    
    // Delete confirmation dialog
    if (showDeleteDialog) {
        DeletePlaylistDialog(
            playlistName = playlist.name,
            onConfirm = {
                showDeleteDialog = false
                onDelete()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}

@Composable
private fun DeletePlaylistDialog(
    playlistName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Delete Playlist",
                color = AppColors.White,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete \"$playlistName\"? This action cannot be undone.",
                color = AppColors.LightGray
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Red
                ),
                border = BorderStroke(
                    width = Dimens.dividerSmallThickness,
                    color = AppColors.White
                )
            ) {
                Text(
                    text = Strings.delete,
                    color = AppColors.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
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

@Preview(showBackground = true)
@Composable
private fun PlaylistsListPreview() {
    val samplePlaylists = listOf(
        Playlist(
            id = "1",
            name = "Chill Vibes",
            description = null,
            songs = List(10) { 
                com.musicapk.domain.model.Song(
                    id = "$it",
                    title = "Song $it",
                    artist = "Artist",
                    album = "Album",
                    duration = 180000,
                    artworkUri = null,
                    audioUri = "",
                    dateAdded = 0
                )
            },
            createdAt = 0L,
            updatedAt = 0L
        ),
        Playlist(
            id = "2",
            name = "Workout Mix",
            description = null,
            songs = List(15) { 
                com.musicapk.domain.model.Song(
                    id = "$it",
                    title = "Song $it",
                    artist = "Artist",
                    album = "Album",
                    duration = 180000,
                    artworkUri = null,
                    audioUri = "",
                    dateAdded = 0
                )
            },
            createdAt = 0L,
            updatedAt = 0L
        )
    )
    
    MusicApkTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            PlaylistsList(
                playlists = samplePlaylists,
                onPlaylistClick = {},
                onCreatePlaylistClick = {},
                onDeletePlaylist = {}
            )
        }
    }
}



