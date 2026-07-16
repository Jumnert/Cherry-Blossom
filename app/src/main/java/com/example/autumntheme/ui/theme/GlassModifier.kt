package com.example.autumntheme.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.glassEffect(
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    alpha: Float = 0.15f,
    tintColor: Color = Color.White,
    accentColor: Color = Color.White
): Modifier = this
    .clip(shape)
    .background(tintColor.copy(alpha = 0.35f))
    .background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = alpha + 0.1f),
                Color.White.copy(alpha = alpha * 0.2f),
            ),
            start = Offset(0f, 0f),
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )
    )
    .background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.15f),
                Color.Transparent
            ),
            endY = 40f
        )
    )
    .border(
        width = 1.dp,
        brush = Brush.linearGradient(
            colors = listOf(
                accentColor.copy(alpha = 0.8f),
                accentColor.copy(alpha = 0.3f),
                Color.White.copy(alpha = 0.05f)
            ),
            start = Offset(0f, 0f),
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        ),
        shape = shape
    )
