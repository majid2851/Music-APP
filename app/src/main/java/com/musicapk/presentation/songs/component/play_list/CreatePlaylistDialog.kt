package com.musicapk.presentation.songs.component.play_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.Strings

@Composable
fun CreatePlaylistDialog(
    onDismiss: () -> Unit,
    onCreate: (name: String, description: String?) -> Unit
) {
    var playlistName by remember { mutableStateOf("") }
    var playlistDescription by remember { mutableStateOf("") }

    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = Strings.createPlaylist,
                color = AppColors.White
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = playlistName,
                    onValueChange = { playlistName = it },
                    label = { Text(Strings.playlistName) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimens.paddingMedium),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = AppColors.White,
                        unfocusedTextColor = AppColors.White,
                        focusedBorderColor = AppColors.White,
                        unfocusedBorderColor = AppColors.White.copy(alpha = 0.5f),
                        cursorColor = AppColors.White,
                        focusedLabelColor = AppColors.White,
                        unfocusedLabelColor = AppColors.White.copy(alpha = 0.7f)
                    )
                )
                
                OutlinedTextField(
                    value = playlistDescription,
                    onValueChange = { playlistDescription = it },
                    label = { Text(Strings.playlistDescription) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = AppColors.White,
                        unfocusedTextColor = AppColors.White,
                        focusedBorderColor = AppColors.White,
                        unfocusedBorderColor = AppColors.White.copy(alpha = 0.5f),
                        cursorColor = AppColors.White,
                        focusedLabelColor = AppColors.White,
                        unfocusedLabelColor = AppColors.White.copy(alpha = 0.7f)
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (playlistName.isNotBlank()) {
                        onCreate(
                            playlistName.trim(),
                            playlistDescription.takeIf { it.isNotBlank() }?.trim()
                        )
                    }
                },
                enabled = playlistName.isNotBlank(),
                border = BorderStroke(
                    width =Dimens.dividerSmallThickness,
                    color = AppColors.White
                )
            ) {
                Text(
                    text = Strings.create,
                    color = AppColors.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = Strings.cancel,
                    color = AppColors.White
                )
            }
        },
        containerColor = AppColors.GradientBlue1,
        titleContentColor = AppColors.White,
        textContentColor = AppColors.White,
        shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
    )
}

@Preview
@Composable
private fun CreatePlaylistDialogPreview() {
    CreatePlaylistDialog(
        onDismiss = {},
        onCreate = { _, _ -> }
    )
}



