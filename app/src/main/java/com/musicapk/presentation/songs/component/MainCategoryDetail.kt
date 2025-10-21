package com.musicapk.presentation.songs.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.R
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme


@Composable
fun MainCategoryDetail()
{
    LazyRow {
        items(5) { // Show 5 items as example
            MainCategoryDetailItem()
        }
    }
}

@Composable
fun MainCategoryDetailItem()
{
    Column(
        modifier = Modifier
            .padding(end = Dimens.paddingMedium)
    )
    {
        Image(
            modifier = Modifier
                .width(Dimens.mainCategoryImgWidth)
                .height(Dimens.mainCategoryImgHeight)
                .clip(RoundedCornerShape(Dimens.cornerRadiusMedium)),
            painter = painterResource(id = R.drawable.img_test),
            contentDescription = ""
        )

        Text(
            modifier = Modifier
                .padding(
                    top = Dimens.paddingSmall,
                    start = Dimens.paddingSmall,
                ),
            text = "R&B Playlist",
            color = AppColors.White,
            fontSize = FontSizes.medium,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
        )

        Text(
            modifier = Modifier
                .padding(start = Dimens.paddingSmall),
            text = "Chill your mind",
            color = AppColors.LightGray,
            fontSize = FontSizes.extraSmall,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
        )
    }
}

@Preview(
    name = "Main Category Detail Item",
    showBackground = true
)
@Composable
private fun MainCategoryDetailItemPreview() {
    MusicApkTheme(darkTheme = true, dynamicColor = false) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            MainCategoryDetailItem()
        }
    }
}

@Preview(
    name = "Main Category Detail List",
    showBackground = true
)
@Composable
private fun MainCategoryDetailPreview() {
    MusicApkTheme(darkTheme = true, dynamicColor = false) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            MainCategoryDetail()
        }
    }
}