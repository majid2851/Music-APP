package com.musicapk.presentation.songs.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.R
import com.musicapk.ui.general_component.gradientScreenBackground
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.Strings

@Composable
fun SearchSongAndArtistAndPlayList()
{
    Row(
        modifier = Modifier
            .background(
                color = AppColors.Gray,
                shape = RoundedCornerShape(Dimens.cornerRadiusSmall)
            )
            .fillMaxWidth()
            .height(Dimens.searchHeight)
            .padding(
                end = Dimens.paddingMedium,
                start = Dimens.paddingMedium,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "",
            colorFilter = ColorFilter.tint(AppColors.White),
            modifier = Modifier
                .size(Dimens.iconSizeSmall),
        )

        Spacer(modifier = Modifier.width(Dimens.spacingSmall))

        Text(
            text = Strings.searchSongPlayslistArtist,
            fontFamily = FontFamily.SansSerif,
            color = AppColors.LightGray,
            fontSize = FontSizes.small
        )
    }
}

@Preview(
    name = "Search Bar - Light",
    showBackground = true
)
@Composable
private fun SearchSongAndArtistAndPlayListPreview() {
    MusicApkTheme(darkTheme = false, dynamicColor = false) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            SearchSongAndArtistAndPlayList()
        }
    }
}

