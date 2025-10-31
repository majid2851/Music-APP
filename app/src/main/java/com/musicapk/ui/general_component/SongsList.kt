package com.musicapk.ui.general_component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
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
    onRemoveSong: ((Song) -> Unit)? = null,
) {
    LazyColumn(
        modifier = modifier.padding(top = Dimens.paddingMedium)
    ) {
        itemsIndexed(
            items = songs,
            key = { _, song -> song.id }
        ) { _, song ->
            if (onRemoveSong != null) {
                SwipeableSongItem(
                    song = song,
                    onSongClick = { onSongClick(song) },
                    onRemove = { onRemoveSong(song) }
                )
            } else {
                SongItem(
                    song = song,
                    onSongClick = { onSongClick(song) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeableSongItem(
    song: Song,
    onSongClick: () -> Unit,
    onRemove: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.StartToEnd, SwipeToDismissBoxValue.EndToStart -> {
                    onRemove()
                    true
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        }
    )
    
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> AppColors.Transparent
                    SwipeToDismissBoxValue.StartToEnd, SwipeToDismissBoxValue.EndToStart -> AppColors.Red
                },
                label = "Swipe background color"
            )
            
            val scale by animateFloatAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1f,
                label = "Icon scale"
            )

            if (dismissState.dismissDirection!=SwipeToDismissBoxValue.Settled){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Dimens.paddingLarge),
                    contentAlignment = when (dismissState.dismissDirection) {
                        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                        else -> Alignment.Center
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.scale(scale),
                        tint = AppColors.White
                    )
                }
            }


        },
        content = {
            SongItem(
                song = song,
                onSongClick = onSongClick,
            )
        }
    )
}

@Composable
fun SongItem(
    song: Song,
    onSongClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.paddingSmall)
            .clip(RoundedCornerShape(Dimens.cornerRadiusMedium))
            .background(AppColors.DarkGray.copy(alpha = 0.2f))
            .clickable { onSongClick() }
            .padding(Dimens.paddingSmall)
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
            color = AppColors.White.copy(alpha = 0.7f),
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
    val sampleSongs = List(100) { index ->
        Song(
            id = "$index",
            title = "Song $index",
            artist = "Artist ${index % 10}",
            album = "Album ${index % 5}",
            duration = 180000 + (index * 1000L),
            artworkUri = null,
            audioUri = "",
            dateAdded = 0
        )
    }
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