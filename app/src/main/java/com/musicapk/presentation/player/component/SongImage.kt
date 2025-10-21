package com.musicapk.presentation.player.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.R
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun SongImage(
    modifier: Modifier= Modifier,
    img:Int
)
{
    Image(
        painter = painterResource(id = img),
        contentDescription = "",
        modifier = modifier
            .height(Dimens.songPlayerImageHeight)
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cornerRadiusMedium)),
        contentScale = ContentScale.Crop,
    )
}

@Preview(showBackground = true)
@Composable
private fun SongImagePreview() {
    SongImage(
        img = R.drawable.img_test,
        modifier = Modifier
            .gradientScreenBackground()
            .padding(Dimens.paddingMedium)
    )
}