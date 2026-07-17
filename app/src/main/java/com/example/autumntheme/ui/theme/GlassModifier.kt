package com.example.autumntheme.ui.theme

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.vibrancy

/**
 * Performant glass effect modifier using Kyant's backdrop blur where available.
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
): Modifier {
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

    // Unconditionally use Kyant backdrop blur for all themes if the backdrop is available and supported
    if (backdrop != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        return this.drawBackdrop(
            backdrop = backdrop,
            shape = { shape },
            effects = {
                vibrancy()
                blur(10f.dp.toPx()) // Beautiful frosted blur
            },
            onDrawSurface = {
                // Paint the card theme tint color
                drawRect(tintColor.copy(alpha = alpha))
            }
        ).border(
            width = 2.dp, // Thicker border to make the gold reflections clearly visible
            brush = primaryBorderBrush,
            shape = shape
        )
    }

    // High performance simulated glass fallback for older Android versions
    return this
        .clip(shape)
        .background(
            Brush.linearGradient(
                0.0f to tintColor.copy(alpha = alpha),
                1.0f to tintColor.copy(alpha = alpha * 0.4f)
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
