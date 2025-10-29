package com.musicapk.ui.theme.styles


import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.musicapk.ui.theme.AppColors

fun Modifier.gradientButtonBackground(): Modifier = this.background(
    brush = Brush.horizontalGradient(
        colors = listOf(
            AppColors.GradientBlue1,
            AppColors.GradientBlue2,
            AppColors.GradientBlue3
        )
    )
)

fun Modifier.gradientScreenBackground(): Modifier = this.background(
    brush = Brush.linearGradient(
        colors = listOf(
            AppColors.BackgroundTop,
            AppColors.GradientBlue1,
            AppColors.BackgroundBottom,
        ),

    )
)


