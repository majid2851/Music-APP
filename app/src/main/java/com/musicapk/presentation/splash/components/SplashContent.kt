package com.musicapk.presentation.splash.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.R
import com.musicapk.presentation.splash.SplashUiState
import com.musicapk.ui.theme.styles.GradientButton
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.Strings

@Composable
fun SplashContent(
    uiState: SplashUiState,
) {
    Box(
        modifier= Modifier
            .fillMaxSize()
    )
    {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "",
        )


        Column(
            modifier = Modifier
                .padding(Dimens.paddingXXXL)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = Strings.musicBaz,
                modifier= Modifier
                    .padding(
                        bottom = Dimens.paddingSmall
                    ),
                fontSize =FontSizes.large,
                color = AppColors.White,
                fontFamily = FontFamily.SansSerif ,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = Strings.emerceIntoMusicWorld,
                modifier= Modifier
                    .padding(
                        start = Dimens.paddingMedium,
                        end = Dimens.paddingMedium,
                        bottom = Dimens.paddingMedium,
                    ),
                fontSize =FontSizes.small,
                textAlign = TextAlign.Center,
                color = AppColors.LightGray,
                fontFamily = FontFamily.SansSerif ,
                fontWeight = FontWeight.SemiBold
            )

            GradientButton(
                modifier = Modifier
                    .padding(bottom = Dimens.paddingLarge),
                text =Strings.getStarted ,
                width =Dimens.buttonWidthNormal ,
                height =Dimens.buttonHeightNormal ,
                onClick = {

                },
            )

        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashContentPreview() {
    MusicApkTheme {
        SplashContent(
            uiState = SplashUiState(
                isLoading = false,
                isInitialized = true,
                error = null
            )
        )
    }
}