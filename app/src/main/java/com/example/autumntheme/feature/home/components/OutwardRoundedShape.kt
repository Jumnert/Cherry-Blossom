package com.example.autumntheme.feature.home.components

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

@Immutable
class OutwardRoundedBottomShape(
    private val cornerRadius: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val radiusPx = with(density) { cornerRadius.toPx() }

        val flatBottomY = size.height - radiusPx

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            quadraticTo(
                x1 = size.width, y1 = flatBottomY,
                x2 = size.width - radiusPx, y2 = flatBottomY
            )
            lineTo(radiusPx, flatBottomY)
            quadraticTo(
                x1 = 0f, y1 = flatBottomY,
                x2 = 0f, y2 = size.height
            )

            close() // straight line back up the left edge to (0, 0)
        }

        return Outline.Generic(path)
    }
}