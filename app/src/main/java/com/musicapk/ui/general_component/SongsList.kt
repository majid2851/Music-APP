package com.musicapk.ui.general_component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.musicapk.domain.model.Song
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes

@Composable
fun SongsList(
    modifier: Modifier = Modifier,
    songs: List<Song> = emptyList(),
    onSongClick: (Song) -> Unit,
)
{
    LazyColumn(
        modifier = modifier
            .padding(top = Dimens.paddingMedium)
    )
    {
        items(
            items = songs,
            key = { song -> song.id } 
        ) { song ->
            SongItem(
                song = song,
                onSongClick = {
                    onSongClick(song)
                }
            )
        }
    }
}


@Composable
fun SongItem(
    song: Song,
    onSongClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSongClick() }
            .padding(bottom = Dimens.paddingSmall)
            .height(Dimens.musicImgSize),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        // Album artwork or placeholder icon
        Box(
            modifier = Modifier
                .size(Dimens.musicImgSize)
                .clip(RoundedCornerShape(Dimens.cornerRadiusMedium))
                .background(AppColors.DarkGray.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            if (song.artworkUri != null) {
                AsyncImage(
                    model = song.artworkUri,
                    contentDescription = song.title,
                    modifier = Modifier.size(Dimens.musicImgSize),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = song.title,
                    modifier = Modifier.size(Dimens.iconSizeLarge),
                    tint = AppColors.White.copy(alpha = 0.5f)
                )
            }
        }

        // Song title and artist
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = Dimens.paddingMedium, end = Dimens.paddingSmall)
        ) {
            Text(
                text = song.title,
                fontSize = FontSizes.medium,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                color = AppColors.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = song.artist,
                fontSize = FontSizes.small,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                color = AppColors.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Duration
        Text(
            text = formatDuration(song.duration),
            fontSize = FontSizes.medium,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.SansSerif,
            color = AppColors.White,
        )
    }
}

private fun formatDuration(durationMillis: Long): String {
    val seconds = (durationMillis / 1000).toInt()
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}

@Preview(
    name = "Songs List",
    showBackground = true,
    backgroundColor = 0xFF1C1B1B
)
@Composable
private fun SongsListPreview() {
    val sampleSongs = listOf(
        Song(
            id = "1",
            title = "Bye Bye",
            artist = "Marshmello, Juice WRLD",
            album = "Unknown",
            duration = 129000,
            artworkUri = null,
            audioUri = "",
            dateAdded = 0
        ),
        Song(
            id = "2",
            title = "Another Song",
            artist = "Artist Name",
            album = "Album Name",
            duration = 180000,
            artworkUri = null,
            audioUri = "",
            dateAdded = 0
        )
    )
    SongsList(
        songs = sampleSongs,
        onSongClick = {}
    )
}

@Preview(
    name = "Song Item",
    showBackground = true,
    backgroundColor = 0xFF1C1B1B
)
@Composable
private fun SongItemPreview() {
    val sampleSong = Song(
        id = "1",
        title = "Bye Bye",
        artist = "Marshmello, Juice WRLD",
        album = "Unknown",
        duration = 129000,
        artworkUri = null,
        audioUri = "",
        dateAdded = 0
    )
    SongItem(
        song = sampleSong,
        onSongClick = {}
    )
}