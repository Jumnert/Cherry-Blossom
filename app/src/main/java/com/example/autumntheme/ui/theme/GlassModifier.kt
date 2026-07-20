package com.example.autumntheme.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop

/**
 * Performant glass effect modifier using lightweight simulated glass surfaces.
 * Displays a thick, luxurious dark golden border for the Gold Premium theme,
 * using an antique, rich bronze-gold gradient.
 */
fun Modifier.glassEffect(
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    alpha: Float = 0.2f,
    tintColor: Color = Color.White,
    accentColor: Color = Color.White,
    backdrop: Backdrop? = null,
    isTrueGlass: Boolean = false,
    solid: Boolean = false,
): Modifier {
    if (solid) {
        return this
            .shadow(elevation = 10.dp, shape = shape, clip = false)
            .clip(shape)
            .background(tintColor)
            .border(width = 1.dp, color = accentColor.copy(alpha = 0.75f), shape = shape)
    }

    // Check if the current accent color represents the Gold Premium theme
    val isGold = accentColor == Color(0xFFE5C158)
    
    // Rich dark golden/bronze gradient simulating antique metal highlights
    val darkGoldGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFA27B3C), // Dark Antique Gold
            Color(0xFFD4AF37), // Metallic Old Gold
            Color(0xFF8C6221), // Deep Bronze / Shadow Gold
            Color(0xFFB89047), // Burnished Gold
            Color(0xFF6F4E18)  // Dark Bronze
        )
    )

    val primaryBorderBrush = if (isGold) {
        darkGoldGradient
    } else {
        Brush.linearGradient(
            0.0f to Color.White.copy(alpha = 0.4f),
            0.25f to accentColor.copy(alpha = 0.9f),
            1.0f to Color.Transparent
        )
    }

    // Always use the simulated surface: per-card live backdrop blur is too costly
    // for scrolling grids and can reduce frame rate sharply on mobile GPUs.
    val surfaceAlpha = if (isTrueGlass) maxOf(alpha, 0.30f) else alpha

    return this
        .clip(shape)
        .background(
            Brush.linearGradient(
                0.0f to tintColor.copy(alpha = surfaceAlpha),
                1.0f to tintColor.copy(alpha = surfaceAlpha * 0.4f)
            )
        )
        .border(
            width = 2.dp,
            brush = primaryBorderBrush,
            shape = shape
        )
        .border(
            width = 1.dp,
            brush = Brush.linearGradient(
                0.0f to Color.Transparent,
                0.5f to accentColor.copy(alpha = 0.1f),
                1.0f to accentColor.copy(alpha = 0.4f)
            ),
            shape = shape
        )
}
