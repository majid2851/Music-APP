package com.musicapk.presentation.songs.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.ui.general_component.SongsList
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.Strings

@Composable
fun YourFavorites()
{
    Column(
        modifier = Modifier
            .padding(top = Dimens.paddingMedium),
    ) {
        Text(
            text = Strings.yourFavorite ,
            fontSize =FontSizes.medium ,
            fontWeight = FontWeight.SemiBold ,
            color =AppColors.White ,
            fontFamily = FontFamily.SansSerif
        )

        SongsList()

    }
}


@Preview(
    name = "Your Favorites Screen",
    showBackground = true,
    backgroundColor = 0xFF1C1B1B
)
@Composable
private fun YourFavoritesPreview() {
    YourFavorites()
}

