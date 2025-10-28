package com.musicapk.presentation.player.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOn
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.ShuffleOn
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.domain.model.RepeatMode
import com.musicapk.ui.general_component.CircleGradientIcon
import com.musicapk.ui.general_component.CustomIcon
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun SongOperators(
    isPlaying: Boolean,
    isShuffleEnabled: Boolean,
    repeatMode: RepeatMode,
    onPlayPauseClick: () -> Unit,
    onSkipPreviousClick: () -> Unit,
    onSkipNextClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(top = Dimens.paddingMedium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomIcon(
            icon = if (isShuffleEnabled) Icons.Default.ShuffleOn else Icons.Default.Shuffle,
            onClick = onShuffleClick,
            tint = if (isShuffleEnabled) AppColors.DarkGray else AppColors.White
        )

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIcon(
                icon = Icons.Default.SkipPrevious,
                onClick = onSkipPreviousClick
            )
            CircleGradientIcon(
                icon = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                onClick = onPlayPauseClick
            )
            CustomIcon(
                icon = Icons.Default.SkipNext,
                onClick = onSkipNextClick
            )
        }

        CustomIcon(
            icon = when (repeatMode) {
                RepeatMode.OFF -> Icons.Default.Repeat
                RepeatMode.ALL -> Icons.Default.RepeatOn
                RepeatMode.ONE -> Icons.Default.RepeatOne
            },
            onClick = onRepeatClick,
            tint = if (repeatMode != RepeatMode.OFF) AppColors.DarkGray else AppColors.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SongOperatorsPreview() {
    SongOperators(
        isPlaying = true,
        isShuffleEnabled = false,
        repeatMode = RepeatMode.OFF,
        onPlayPauseClick = {},
        onSkipPreviousClick = {},
        onSkipNextClick = {},
        onShuffleClick = {},
        onRepeatClick = {},
        modifier = Modifier.gradientScreenBackground()
    )
}