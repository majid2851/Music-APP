package com.musicapk.presentation.player.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.ui.general_component.CustomIcon

@Composable
fun BackAndMoreIcons()
{
    Row()
    {
        CustomIcon(
            icon = Icons.Default.ChevronLeft
        )
        Spacer(modifier = Modifier.weight(1f))

        CustomIcon(
            icon = Icons.Default.MoreHoriz
        )


    }
}

@Composable
@Preview
private fun BackAndMoreIconsPreview(){
    BackAndMoreIcons()
}