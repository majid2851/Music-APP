package com.musicapk.presentation.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.R
import com.musicapk.presentation.player.component.BackAndMoreIcons
import com.musicapk.presentation.player.component.SongImage
import com.musicapk.presentation.player.component.SongInfo
import com.musicapk.presentation.player.component.SongOperators
import com.musicapk.presentation.player.component.SongProgressBar
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.styles.screenPaddings

@Composable
fun Player()
{
    Column(
        modifier = Modifier
            .gradientScreenBackground()
            .fillMaxSize()
            .screenPaddings()

    ){
        BackAndMoreIcons()

        SongImage(
            img= R.drawable.img_test,
            modifier = Modifier
                .padding(
                    top = Dimens.paddingMedium,
                    bottom = Dimens.paddingLarge,
                )
        )

        SongInfo()

        SongProgressBar()

        SongOperators()
    }

}


@Preview
@Composable
private fun PlayerPreview()
{
    Player()
}
