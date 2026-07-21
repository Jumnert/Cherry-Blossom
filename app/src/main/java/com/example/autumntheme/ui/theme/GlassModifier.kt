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
    frosted: Boolean = false,
    solid: Boolean = false,
): Modifier {
    val isGold = accentColor == Color(0xFFE5C158) ||
        accentColor == Color(0xFFD7B45F) ||
        accentColor == Color(0xFFC5A059)

    // A vertically shifting antique-gold foil finish: dark edge, bright
    // champagne highlight, then a deep burnished-gold return.
    val goldBorderGradient = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to Color(0xFF6F4814),
            0.18f to Color(0xFFC58C26),
            0.42f to Color(0xFFFFF1A6),
            0.56f to Color(0xFFD89E32),
            0.76f to Color(0xFF8A570E),
            1.0f to Color(0xFFF2D36D)
        )
    )

    if (solid) {
        return this
            .shadow(elevation = 10.dp, shape = shape, clip = false)
            .clip(shape)
            .background(tintColor)
            .border(
                width = 2.dp,
                brush = if (isGold) goldBorderGradient else Brush.linearGradient(
                    colors = listOf(accentColor.copy(alpha = 0.88f), accentColor.copy(alpha = 0.88f))
                ),
                shape = shape
            )
    }

    // Rich dark golden/bronze gradient simulating antique metal highlights
    val primaryBorderBrush = if (isGold) {
        goldBorderGradient
    } else {
        Brush.linearGradient(
            0.0f to Color.White.copy(alpha = 0.4f),
            0.25f to accentColor.copy(alpha = 0.9f),
            1.0f to Color.Transparent
        )
    }

    // Always use the simulated surface: per-card live backdrop blur is too costly
    // for scrolling grids and can reduce frame rate sharply on mobile GPUs.
    // A low-cost frosted-glass approximation: layered translucent gradients and
    // highlight borders. It has no live blur, RenderEffect, or off-screen capture.
    val surfaceAlpha = when {
        isTrueGlass -> maxOf(alpha, 0.36f)
        frosted -> maxOf(alpha + 0.22f, 0.38f)
        else -> alpha
    }
    val midSurfaceAlpha = if (frosted) surfaceAlpha * 0.78f else if (isTrueGlass) surfaceAlpha * 0.68f else surfaceAlpha * 0.70f
    val edgeSurfaceAlpha = if (frosted) surfaceAlpha * 0.52f else if (isTrueGlass) surfaceAlpha * 0.30f else surfaceAlpha * 0.40f

    return this
        .shadow(
            elevation = if (frosted) 12.dp else 6.dp,
            shape = shape,
            clip = false,
            ambientColor = Color.Black.copy(alpha = if (frosted) 0.18f else 0.10f),
            spotColor = Color.Black.copy(alpha = if (frosted) 0.24f else 0.14f)
        )
        .clip(shape)
        .background(
            Brush.radialGradient(
                0.0f to Color.White.copy(alpha = if (frosted) 0.20f else 0.06f),
                1.0f to Color.Transparent
            )
        )
        .background(
            Brush.linearGradient(
                0.0f to tintColor.copy(alpha = surfaceAlpha),
                0.45f to tintColor.copy(alpha = midSurfaceAlpha),
                1.0f to tintColor.copy(alpha = edgeSurfaceAlpha)
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
