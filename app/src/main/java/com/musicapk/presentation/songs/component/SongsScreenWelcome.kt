package com.musicapk.presentation.songs.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.ui.general_component.gradientScreenBackground
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.Strings


@Composable
fun SongsScreenWelcome()
{

    Column(
        modifier = Modifier
            .padding(bottom = Dimens.paddingLarge)
    ) {
        Text(
            text = Strings.welcomeBack,
            color = AppColors.White,
            fontSize = FontSizes.medium,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )

        Text(
            text = Strings.whatDoYouFeelToday,
            color = AppColors.LightGray,
            fontFamily = FontFamily.SansSerif,
            fontSize = FontSizes.small,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(
    name = "Songs Screen Welcome - Light",
    showBackground = true
)
@Composable
private fun SongsScreenWelcomePreview() {
    MusicApkTheme(darkTheme = false, dynamicColor = false) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            SongsScreenWelcome()
        }
    }
}
