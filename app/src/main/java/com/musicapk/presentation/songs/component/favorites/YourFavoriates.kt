package com.musicapk.presentation.songs.component.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.domain.model.Song
import com.musicapk.ui.general_component.SongsList
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.Strings

@Composable
fun YourFavorites(
    songs: List<Song>,
    onSongClick: (Song) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = Dimens.paddingMedium),
    ) {
        Text(
            text = Strings.yourFavorite,
            fontSize = FontSizes.medium,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.White,
            fontFamily = FontFamily.SansSerif
        )

        if (songs.isEmpty()) {
            Text(
                text = Strings.noFavorites,
                fontSize = FontSizes.small,
                color = AppColors.LightGray,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(top = Dimens.paddingSmall)
            )
        } else {
            SongsList(
                songs = songs,
                onSongClick = onSongClick
            )
        }
    }
}


@Preview(
    name = "Your Favorites Screen",
    showBackground = true,
    backgroundColor = 0xFF1C1B1B
)
@Composable
private fun YourFavoritesPreview() {
    val sampleSongs = listOf(
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
        )
    )
    YourFavorites(
        songs = sampleSongs,
        onSongClick = {}
    )
}

