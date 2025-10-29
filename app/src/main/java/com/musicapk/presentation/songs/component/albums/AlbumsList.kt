package com.musicapk.presentation.songs.component.albums

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
import androidx.compose.material.icons.filled.Album
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.domain.model.Album
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.Strings
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun AlbumsList(
    albums: List<Album>,
    onAlbumClick: (Album) -> Unit,
    modifier: Modifier = Modifier
) {
    if (albums.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = Strings.noAlbumsFound,
                color = AppColors.LightGray,
                fontSize = FontSizes.medium,
                fontFamily = FontFamily.SansSerif
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {
            items(albums) { album ->
                AlbumListItem(
                    album = album,
                    onClick = { onAlbumClick(album) }
                )
            }
        }
    }
}

@Composable
private fun AlbumListItem(
    album: Album,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = Dimens.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Album artwork
        Box(
            modifier = Modifier
                .size(Dimens.albumArtworkSize)
                .clip(RoundedCornerShape(Dimens.cornerRadiusSmall))
                .background(AppColors.DarkGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Album,
                contentDescription = album.name,
                modifier = Modifier.size(Dimens.iconSizeMedium),
                tint = AppColors.White.copy(alpha = 0.7f)
            )
        }

        // Album info
        Column(
            modifier = Modifier
                .padding(start = Dimens.paddingMedium)
                .weight(1f)
        ) {
            Text(
                text = album.name,
                color = AppColors.White,
                fontSize = FontSizes.medium,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                maxLines = 1
            )
            
            Text(
                text = album.artist,
                color = AppColors.LightGray,
                fontSize = FontSizes.small,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(top = Dimens.paddingExtraSmall),
                maxLines = 1
            )
            
            Text(
                text = if (album.songCount == 1) {
                    "1 song"
                } else {
                    "${album.songCount} songs"
                },
                color = AppColors.LightGray,
                fontSize = FontSizes.extraSmall,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(top = Dimens.paddingExtraSmall)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlbumsListPreview() {
    val sampleAlbums = listOf(
        Album(
            id = "1",
            name = "Greatest Hits",
            artist = "Various Artists",
            artworkUri = null,
            songCount = 15
        ),
        Album(
            id = "2",
            name = "Summer Vibes 2024",
            artist = "DJ Cool",
            artworkUri = null,
            songCount = 20
        ),
        Album(
            id = "3",
            name = "Classical Collection",
            artist = "Mozart",
            artworkUri = null,
            songCount = 12
        )
    )
    
    MusicApkTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            AlbumsList(
                albums = sampleAlbums,
                onAlbumClick = {}
            )
        }
    }
}

