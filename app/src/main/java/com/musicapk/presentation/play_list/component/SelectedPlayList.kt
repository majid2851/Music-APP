package com.musicapk.presentation.play_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.R
import com.musicapk.presentation.player.component.BackAndMoreIcons
import com.musicapk.ui.general_component.CircleGradientIcon
import com.musicapk.ui.general_component.CustomIcon
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes

@Composable
fun SelectedPlayList()
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.musicOverviewHeight)
            .clip(RoundedCornerShape(Dimens.cornerRadiusMedium))
    )
    {
        Image(
            painter = painterResource(
                id = R.drawable.img_test
            ) ,
            contentScale = ContentScale.Crop,
            contentDescription ="",
            modifier = Modifier
                .fillMaxSize()

        )

        Column(
            modifier = Modifier.padding(
                Dimens.paddingLarge
            )
        )
        {
            BackAndMoreIcons()

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(bottom = Dimens.paddingLarge)

            )
            {

                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text ="Bye Bye",
                        fontSize = FontSizes.large ,
                        fontWeight = FontWeight.SemiBold ,
                        fontFamily = FontFamily.SansSerif ,
                        color = AppColors.White,
                    )

                    Text(
                        text ="Marshmello, Juice WRLD" ,
                        fontSize = FontSizes.small ,
                        fontWeight = FontWeight.Medium ,
                        fontFamily = FontFamily.SansSerif ,
                        color = AppColors.DarkGray ,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                )
                {
                    CustomIcon(icon = Icons.Outlined.FavoriteBorder)

                    CircleGradientIcon(
                        icon =Icons.Default.PlayArrow
                    )

                }

            }

        }

    }


}

@Preview(
    name = "Selected Music Overview",
    showBackground = true,
    backgroundColor = 0xFF1C1B1B
)
@Composable
private fun SelectedMusicOverviewPreview() {
    SelectedPlayList()
}
