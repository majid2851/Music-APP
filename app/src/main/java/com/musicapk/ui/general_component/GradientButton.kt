package com.musicapk.ui.general_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.musicapk.ui.theme.*

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp  ,
    height: Dp ,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(width)
            .height(height),
        shape = RoundedCornerShape(Dimens.cornerRadiusMedium),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.Transparent
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        AppColors.GradientPurple1,
                        AppColors.GradientPink,
                        AppColors.GradientPurple2
                    )
                )
            ),
            contentAlignment = Alignment.Center
        ) {
        Text(
            text = text,
            color = AppColors.White,
            fontWeight = FontWeight.Bold,
            fontSize = FontSizes.default
        )
    }
}
}

@Preview(showBackground = true)
@Composable
fun GradientButtonPreview() {
    GradientButton(
        text = "Get Started",
        onClick = {},
        width = 200.dp,
        height = 50.dp
    )
}