package com.musicapk.presentation.music_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.presentation.music_overview.component.SelectedMusicOverview
import com.musicapk.ui.general_component.SongsList
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.styles.screenPaddings

@Composable
fun MusicOverview()
{
    Column(
        modifier = Modifier
            .gradientScreenBackground()
            .fillMaxSize()
    ){

        SelectedMusicOverview()

        SongsList(
            modifier=Modifier
                .screenPaddings()
        )

    }
}

@Preview(
    name = "Music Overview Screen",
    showBackground = true
)
@Composable
private fun MusicOverviewPreview() {
    MusicOverview()
}