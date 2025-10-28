package com.musicapk.presentation.play_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.presentation.play_list.component.SelectedPlayList
import com.musicapk.ui.general_component.SongsList
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.styles.screenPaddings

@Composable
fun MusicPlayList(
    onSongClick:()->Unit,
)
{
    Column(
        modifier = Modifier
            .gradientScreenBackground()
            .fillMaxSize()
    ){

        SelectedPlayList()

        SongsList(
            modifier=Modifier
                .screenPaddings(),
            onSongClick = {
                onSongClick()
            }
        )

    }
}

@Preview(
    name = "Music Overview Screen",
    showBackground = true
)
@Composable
private fun MusicOverviewPreview() {
    MusicPlayList(
        onSongClick = {}
    )
}