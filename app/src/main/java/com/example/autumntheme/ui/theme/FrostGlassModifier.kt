package com.example.autumntheme.ui.theme

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * A custom Jetpack Compose Modifier that replicates the frosted glass card effect
 * seen in premium mobile applications (e.g. ACLEDA Bank, iOS frosted glass).
 *
 * Supports hardware-accelerated RenderEffect blurring on API 31+ and falls back
 * to high-performance RenderScript downscaled blurring on API 24-30.
 */
@Composable
fun Modifier.frostGlass(
    blurRadius: Float = 20f,
    tintAlpha: Float = 0.45f,
    cornerRadius: Dp = 20.dp,
    borderAlpha: Float = 0.3f
): Modifier {
    val context = LocalContext.current
    val view = LocalView.current
    val shape = RoundedCornerShape(cornerRadius)

    var positionInRoot by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    var blurredBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var lastCapturedTime by remember { mutableLongStateOf(0L) }

    val blurModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // API 31+: RenderEffect via graphicsLayer with TileMode.Clamp
        Modifier.graphicsLayer {
            renderEffect = BlurEffect(
                radiusX = blurRadius,
                radiusY = blurRadius,
                edgeTreatment = TileMode.Clamp
            )
        }
    } else {
        // API 24-30: RenderScript ScriptIntrinsicBlur with 25% downscale
        Modifier.drawBehind {
            if (size.width > 0 && size.height > 0) {
                val now = System.currentTimeMillis()
                // Cache the blurred bitmap and only capture/re-blur if size changes or 500ms has elapsed
                if (blurredBitmap == null || now - lastCapturedTime > 500) {
                    try {
                        val viewBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
                        val viewCanvas = Canvas(viewBitmap)
                        view.draw(viewCanvas)

                        val posX = positionInRoot.x.roundToInt().coerceIn(0, view.width - 1)
                        val posY = positionInRoot.y.roundToInt().coerceIn(0, view.height - 1)
                        val cropW = size.width.coerceAtMost(view.width - posX)
                        val cropH = size.height.coerceAtMost(view.height - posY)

                        if (cropW > 0 && cropH > 0) {
                            val cropped = Bitmap.createBitmap(viewBitmap, posX, posY, cropW, cropH)
                            val downscaled = Bitmap.createScaledBitmap(
                                cropped, 
                                (cropW * 0.25f).roundToInt().coerceAtLeast(1), 
                                (cropH * 0.25f).roundToInt().coerceAtLeast(1), 
                                false
                            )
                            val blurred = Bitmap.createBitmap(downscaled.width, downscaled.height, Bitmap.Config.ARGB_8888)

                            val rs = RenderScript.create(context)
                            val intrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
                            val input = Allocation.createFromBitmap(rs, downscaled)
                            val output = Allocation.createFromBitmap(rs, blurred)
                            intrinsic.setRadius(blurRadius.coerceIn(1f, 25f))
                            intrinsic.setInput(input)
                            intrinsic.forEach(output)
                            output.copyTo(blurred)
                            rs.destroy()

                            val upscaled = Bitmap.createScaledBitmap(blurred, cropW, cropH, true)
                            blurredBitmap = upscaled.asImageBitmap()
                            lastCapturedTime = now
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                blurredBitmap?.let { bmp ->
                    drawImage(image = bmp)
                }
            }
        }
    }

    return this
        .shadow(
            elevation = 8.dp,
            shape = shape,
            clip = false,
            ambientColor = Color.Black.copy(alpha = 0.12f),
            spotColor = Color.Black.copy(alpha = 0.20f)
        )
        .onGloballyPositioned { coordinates ->
            positionInRoot = coordinates.positionInRoot()
            size = coordinates.size
        }
        .clip(shape)
        .then(blurModifier)
        .background(Color.White.copy(alpha = tintAlpha))
        .border(
            width = 1.dp,
            color = Color.White.copy(alpha = borderAlpha),
            shape = shape
        )
}

/**
 * A sample FrostCard composable that wraps its contents inside a Box
 * styled with the custom [frostGlass] modifier.
 */
@Composable
fun FrostCard(
    modifier: Modifier = Modifier,
    blurRadius: Float = 20f,
    tintAlpha: Float = 0.48f,
    cornerRadius: Dp = 20.dp,
    borderAlpha: Float = 0.3f,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .frostGlass(
                blurRadius = blurRadius,
                tintAlpha = tintAlpha,
                cornerRadius = cornerRadius,
                borderAlpha = borderAlpha
            ),
        content = content
    )
}
