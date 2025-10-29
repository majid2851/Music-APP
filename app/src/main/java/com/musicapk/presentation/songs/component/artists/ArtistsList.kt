package com.musicapk.presentation.songs.component.artists

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.domain.model.Artist
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.Strings
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun ArtistsList(
    artists: List<Artist>,
    onArtistClick: (Artist) -> Unit,
    modifier: Modifier = Modifier
) {
    if (artists.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = Strings.noArtistsFound,
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
            items(artists) { artist ->
                ArtistListItem(
                    artist = artist,
                    onClick = { onArtistClick(artist) }
                )
            }
        }
    }
}

@Composable
private fun ArtistListItem(
    artist: Artist,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = Dimens.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Artist image (circular)
        Box(
            modifier = Modifier
                .size(Dimens.artistImageSize)
                .clip(CircleShape)
                .background(AppColors.DarkGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = artist.name,
                modifier = Modifier.size(Dimens.iconSizeMedium),
                tint = AppColors.White.copy(alpha = 0.7f)
            )
        }

        // Artist info
        Column(
            modifier = Modifier
                .padding(start = Dimens.paddingMedium)
                .weight(1f)
        ) {
            Text(
                text = artist.name,
                color = AppColors.White,
                fontSize = FontSizes.medium,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                maxLines = 1
            )
            
            val songsText = if (artist.songCount == 1) {
                "1 song"
            } else {
                "${artist.songCount} songs"
            }
            
            val albumsText = if (artist.albumCount > 0) {
                if (artist.albumCount == 1) {
                    " · 1 album"
                } else {
                    " · ${artist.albumCount} albums"
                }
            } else {
                ""
            }
            
            val infoText = songsText + albumsText
            
            Text(
                text = infoText,
                color = AppColors.LightGray,
                fontSize = FontSizes.small,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(top = Dimens.paddingExtraSmall)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArtistsListPreview() {
    val sampleArtists = listOf(
        Artist(
            id = "1",
            name = "The Weeknd",
            artworkUri = null,
            songCount = 45,
            albumCount = 5
        ),
        Artist(
            id = "2",
            name = "Ariana Grande",
            artworkUri = null,
            songCount = 38,
            albumCount = 6
        ),
        Artist(
            id = "3",
            name = "Drake",
            artworkUri = null,
            songCount = 52,
            albumCount = 7
        )
    )
    
    MusicApkTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            ArtistsList(
                artists = sampleArtists,
                onArtistClick = {}
            )
        }
    }
}

