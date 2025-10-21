package com.musicapk.presentation.player.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun SongProgressBar(
    modifier: Modifier = Modifier
)
{
    var sliderPosition by remember { mutableFloatStateOf(84f) }

    Column(
        modifier = modifier.fillMaxWidth()
    )
    {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..238f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = AppColors.White,
                activeTrackColor = AppColors.White,
                inactiveTrackColor = AppColors.DarkGray
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = Dimens.paddingSmall,
                    start = Dimens.paddingSmall,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {
            Text(
                text = formatTime(sliderPosition.toInt()),
                fontFamily = FontFamily.SansSerif,
                fontSize = FontSizes.medium,
                color = AppColors.White
            )

            Text(
                text = formatTime(238),
                fontFamily = FontFamily.SansSerif,
                fontSize = FontSizes.medium,
                color = AppColors.White
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}

@Preview(showBackground = true)
@Composable
private fun SongProgressBarPreview() {
    SongProgressBar(
        modifier = Modifier
            .gradientScreenBackground()
            .padding(
                top = Dimens.paddingMedium,
                bottom = Dimens.paddingMedium,
            )
    )
}