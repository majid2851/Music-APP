package com.musicapk.presentation.songs.component.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.domain.model.Song
import com.musicapk.ui.general_component.SongItem
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.Strings
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun FavoritesList(
    favoriteSongs: List<Song>,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    if (favoriteSongs.isEmpty()) {
        EmptyFavoritesState(modifier = modifier)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
        ) {
            items(favoriteSongs) { song ->
                SongItem(
                    song = song,
                    onSongClick = { onSongClick(song) }
                )
            }
        }
    }
}

@Composable
private fun EmptyFavoritesState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = Strings.noFavoriteSongs,
                color = AppColors.White,
                fontSize = FontSizes.large,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = Strings.addSongsToFavorites,
                color = AppColors.LightGray,
                fontSize = FontSizes.medium,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = Dimens.paddingMedium)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesListEmptyPreview() {
    MusicApkTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            FavoritesList(
                favoriteSongs = emptyList(),
                onSongClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesListPreview() {
    val sampleFavoriteSongs = listOf(
        Song(
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
        Song(
            id = "2",
            title = "Blinding Lights",
            artist = "The Weeknd",
            album = "After Hours",
            duration = 200000,
            artworkUri = null,
            audioUri = "",
            dateAdded = 0,
            isFavorite = true
        ),
        Song(
            id = "3",
            title = "Levitating",
            artist = "Dua Lipa",
            album = "Future Nostalgia",
            duration = 203000,
            artworkUri = null,
            audioUri = "",
            dateAdded = 0,
            isFavorite = true
        )
    )
    
    MusicApkTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            FavoritesList(
                favoriteSongs = sampleFavoriteSongs,
                onSongClick = {}
            )
        }
    }
}



