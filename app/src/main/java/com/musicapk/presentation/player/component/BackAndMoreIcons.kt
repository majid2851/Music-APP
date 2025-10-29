package com.musicapk.presentation.player.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.ui.general_component.CustomIcon
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.Strings

@Composable
fun BackAndMoreIcons(
    onBackClick: () -> Unit = {},
    onAddToPlaylist: () -> Unit = {},
    onShare: () -> Unit = {},
    onViewDetails: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }
    
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        CustomIcon(
            icon = Icons.Default.ArrowBack,
            onClick = onBackClick
        )
        
        Spacer(modifier = Modifier.weight(1f))

        Column {
            CustomIcon(
                icon = Icons.Default.MoreVert,
                onClick = { showMenu = true }
            )

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier
                     .background(AppColors.GradientBlue1)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = Strings.addToPlaylist,
                            color = AppColors.White
                        )
                    },
                    onClick = {
                        showMenu = false
                        onAddToPlaylist()
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = AppColors.White
                    )
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = Strings.share,
                            color = AppColors.White
                        )
                    },
                    onClick = {
                        showMenu = false
                        onShare()
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = AppColors.White
                    )
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = Strings.viewDetails,
                            color = AppColors.White
                        )
                    },
                    onClick = {
                        showMenu = false
                        onViewDetails()
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = AppColors.White
                    )
                )
            }
        }

    }
}

@Composable
@Preview
private fun BackAndMoreIconsPreview() {
    BackAndMoreIcons()
}