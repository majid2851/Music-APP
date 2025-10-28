package com.musicapk.presentation.songs.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.ui.general_component.CustomIcon
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.Strings
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun SearchSongAndArtistAndPlayList(
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {}
)
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
        CustomIcon(
            icon = Icons.Default.Search,
            tint = AppColors.LightGray
        )

        Spacer(modifier = Modifier.width(Dimens.spacingSmall))

        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                color = AppColors.White,
                fontSize = FontSizes.small
            ),
            cursorBrush = SolidColor(AppColors.White),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = Strings.searchSongPlayslistArtist,
                            fontFamily = FontFamily.SansSerif,
                            color = AppColors.LightGray,
                            fontSize = FontSizes.default
                        )
                    }
                    innerTextField()
                }
            }
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

