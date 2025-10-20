package com.musicapk.ui.general_component


import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.musicapk.ui.theme.AppColors

fun Modifier.gradientButtonBackground(): Modifier = this.background(
    brush = Brush.horizontalGradient(
        colors = listOf(
            AppColors.GradientPurple1,
            AppColors.GradientPink,
            AppColors.GradientPurple2
        )
    )
)

fun Modifier.gradientScreenBackground(): Modifier = this.background(
    brush = Brush.linearGradient(
        colors = listOf(
            AppColors.BackgroundTop,
            AppColors.GradientPurple1,
            AppColors.BackgroundBottom,
        ),

    )
)


