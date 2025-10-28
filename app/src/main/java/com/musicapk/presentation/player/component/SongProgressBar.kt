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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
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
    currentPosition: Long,
    duration: Long,
    onSeek: (Long) -> Unit,
    onFinishSong: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSeconds = (currentPosition / 1000).toFloat()
    val durationSeconds = (duration / 1000).toFloat()

    var sliderPosition by remember { mutableStateOf(currentSeconds) }
    var isUserInteracting by remember { mutableStateOf(false) }

    if (!isUserInteracting) {
        sliderPosition = currentSeconds
    }

    LaunchedEffect(currentPosition) {
         if (duration > 0 && currentPosition >= duration - 1000) {
            onFinishSong()
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Slider(
            value = if (durationSeconds > 0) sliderPosition else 0f,
            onValueChange = {
                isUserInteracting = true
                sliderPosition = it
            },
            onValueChangeFinished = {
                isUserInteracting = false
                onSeek((sliderPosition * 1000).toLong())
            },
            valueRange = 0f..(durationSeconds.takeIf { it > 0 } ?: 1f),
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(currentSeconds.toInt()),
                fontFamily = FontFamily.SansSerif,
                fontSize = FontSizes.medium,
                color = AppColors.White
            )

            Text(
                text = formatTime(durationSeconds.toInt()),
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
        currentPosition = 84000L, // 1:24
        duration = 238000L, // 3:58
        onSeek = {},
        onFinishSong = {},
        modifier = Modifier
            .gradientScreenBackground()
            .padding(
                top = Dimens.paddingMedium,
                bottom = Dimens.paddingMedium,
            )
    )
}