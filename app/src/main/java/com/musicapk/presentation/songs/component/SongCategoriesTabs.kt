package com.musicapk.presentation.songs.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.presentation.songs.model.SongCategoryEnum
import com.musicapk.ui.theme.styles.gradientButtonBackground
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme

@Composable
fun SongCategoriesTabs()
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top=Dimens.paddingLarge,
                bottom = Dimens.paddingMedium,
            )
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SongCategoryEnum.entries.forEach {
            SongCategoryItem(title = it.title)
        }
    }
}

@Composable
private fun SongCategoryItem(
    title:String,
) {
    Column(
        modifier=Modifier
            .width(Dimens.musicCategoryItemWidth)
            .padding(
                end = Dimens.paddingSmall,
                start = Dimens.paddingSmall,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Text(
            text = title,
            modifier = Modifier.padding(
                bottom = Dimens.paddingSmall
            ),
            fontSize = FontSizes.medium,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.White,
            fontFamily = FontFamily.SansSerif,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dividerNormalThickness)
                .gradientButtonBackground()
        )

    }
}



@Preview(
    name = "Music Categories Tabs - Light",
    showBackground = true
)
@Composable
private fun MusicCategoriesTabsPreview() {
    MusicApkTheme(darkTheme = false, dynamicColor = false) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            SongCategoriesTabs()
        }
    }
}

