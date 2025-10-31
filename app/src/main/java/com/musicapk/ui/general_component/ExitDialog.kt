package com.musicapk.ui.general_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.Strings

@Composable
fun ExitDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingMedium),
            shape = RoundedCornerShape(Dimens.cornerRadiusLarge),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.GradientBlue1
            )
        ) {
            Column(
                modifier = Modifier.padding(Dimens.paddingExtraLarge)
            ) {
                Text(
                    text = Strings.exitApp,
                    fontSize = FontSizes.large,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.White
                )
                
                Spacer(modifier = Modifier.height(Dimens.paddingMedium))
                
                Text(
                    text = Strings.exitAppMessage,
                    fontSize = FontSizes.medium,
                    color = AppColors.LightGray
                )
                
                Spacer(modifier = Modifier.height(Dimens.paddingExtraLarge))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(
                            text = Strings.cancel,
                            color = AppColors.LightGray,
                            fontSize = FontSizes.medium
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(Dimens.paddingMedium))
                    
                    OutlinedButton(
                        onClick = onConfirm,
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = AppColors.Transparent,
                            contentColor = AppColors.White
                        ),
                        border = BorderStroke(
                            width =Dimens.dividerSmallThickness,
                            color = AppColors.White
                        ),
                        shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
                    ) {
                        Text(
                            text = Strings.exit,
                            color = AppColors.White,
                            fontSize = FontSizes.medium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExitDialogPreview() {
    ExitDialog(
        onDismiss = {},
        onConfirm = {}
    )
}

