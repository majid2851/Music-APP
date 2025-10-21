package com.musicapk.ui.general_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.R
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes

@Composable
fun SongsList(
    modifier: Modifier= Modifier,
)
{
    LazyColumn(
        modifier= modifier
            .padding(top = Dimens.paddingMedium)
    )
    {
        items(10){
            SongItem()
        }
    }
}


@Composable
private fun SongItem() {

    Row(
        modifier = Modifier
            .padding(bottom = Dimens.paddingSmall)
            .height(Dimens.musicImgSize)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            painter = painterResource(id = R.drawable.img_test) ,
            contentDescription ="",
            modifier= Modifier
                .width(Dimens.musicImgSize)
                .height(Dimens.musicImgSize)
                .clip(RoundedCornerShape(Dimens.cornerRadiusMedium))
        )

        Column(
            modifier = Modifier
                .padding(start = Dimens.paddingMedium)
        ) {
            Text(
                text ="Bye Bye",
                fontSize = FontSizes.medium ,
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

        Spacer(modifier=Modifier.weight(1f))


        Text(
            text ="2:09" ,
            fontSize = FontSizes.medium ,
            fontWeight = FontWeight.Medium ,
            fontFamily = FontFamily.SansSerif ,
            color = AppColors.White ,
        )

    }

}

@Preview(
    name = "Songs List",
    showBackground = true,
    backgroundColor = 0xFF1C1B1B
)
@Composable
private fun SongsListPreview() {
    SongsList()
}

@Preview(
    name = "Song Item",
    showBackground = true,
    backgroundColor = 0xFF1C1B1B
)
@Composable
private fun SongItemPreview() {
    SongItem()
}