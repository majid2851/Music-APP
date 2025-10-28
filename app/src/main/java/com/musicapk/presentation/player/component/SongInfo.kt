package com.musicapk.presentation.player.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.ui.general_component.CustomIcon
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun SongInfo(
    title: String,
    artist: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = title,
                fontFamily = FontFamily.SansSerif,
                fontSize = FontSizes.large,
                color = AppColors.White,
            )

            Text(
                text = artist,
                fontFamily = FontFamily.SansSerif,
                fontSize = FontSizes.medium,
                color = AppColors.DarkGray,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        CustomIcon(
            icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            onClick = onFavoriteClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SongInfoPreview() {
    SongInfo(
        title = "You Right",
        artist = "Doja Cat, The Weeknd",
        isFavorite = true,
        onFavoriteClick = {},
        modifier = Modifier.gradientScreenBackground()
    )
}
