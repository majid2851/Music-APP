package com.musicapk.presentation.player.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.domain.model.Song
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.Strings
import java.util.concurrent.TimeUnit

@Composable
fun SongDetailsDialog(
    song: Song,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = Strings.songDetails,
                fontWeight = FontWeight.Bold,
                color = AppColors.White
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                DetailRow(
                    label = Strings.title,
                    value = song.title
                )
                
                DetailRow(
                    label = Strings.artist,
                    value = song.artist
                )
                
                DetailRow(
                    label = Strings.album,
                    value = song.album
                )
                
                DetailRow(
                    label = Strings.duration,
                    value = formatDuration(song.duration)
                )
                
                if (song.audioUri.isNotEmpty()) {
                    DetailRow(
                        label = Strings.filePath,
                        value = song.audioUri,
                        isPath = true
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = Strings.close,
                    color = AppColors.White
                )
            }
        },
        containerColor = AppColors.DarkBackground,
        titleContentColor = AppColors.White,
        textContentColor = AppColors.White,
        shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
    )
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    isPath: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.paddingSmall)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = FontSizes.small,
            color = AppColors.LightGray
        )
        
        Text(
            text = value,
            fontSize = if (isPath) FontSizes.extraSmall else FontSizes.medium,
            color = AppColors.White,
            modifier = Modifier.padding(top = Dimens.paddingExtraSmall)
        )
    }
}

private fun formatDuration(durationMs: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs) % 60
    return String.format("%d:%02d", minutes, seconds)
}

@Preview
@Composable
private fun SongDetailsDialogPreview() {
    val sampleSong = Song(
        id = "1",
        title = "Bye Bye",
        artist = "Marshmello, Juice WRLD",
        album = "Unknown",
        duration = 129000,
        artworkUri = null,
        audioUri = "/storage/emulated/0/Music/bye_bye.mp3",
        dateAdded = 0,
        isFavorite = true
    )
    
    SongDetailsDialog(
        song = sampleSong,
        onDismiss = {}
    )
}



