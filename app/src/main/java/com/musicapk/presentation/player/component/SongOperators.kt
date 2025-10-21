package com.musicapk.presentation.player.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.ui.general_component.CircleGradientIcon
import com.musicapk.ui.general_component.CustomIcon
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun SongOperators(
    modifier: Modifier = Modifier
)
{
    Row(
        modifier = modifier
            .padding(top = Dimens.paddingMedium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        CustomIcon(icon = Icons.Default.Shuffle)

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingLarge),
            verticalAlignment = Alignment.CenterVertically
        ){
            CustomIcon(icon = Icons.Default.SkipPrevious)
            CircleGradientIcon(icon = Icons.Default.Pause)
            CustomIcon(icon = Icons.Default.SkipNext)
        }

        CustomIcon(
            icon = Icons.Default.Repeat
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SongOperatorsPreview() {
    SongOperators(
        modifier = Modifier.gradientScreenBackground()
    )
}