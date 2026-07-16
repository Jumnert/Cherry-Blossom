package com.example.autumntheme.feature.receipt

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.autumntheme.R

@Composable
fun ReceiptScreen() {
    val navyBackground = Color(0xFF0C1E36)
    val scrollState = rememberScrollState()

    val confettiCompositionResult = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val confettiComposition = confettiCompositionResult.value

    val density = LocalDensity.current
    val cornerRadiusPx = with(density) { 20.dp.toPx() }
    val cutoutRadiusPx = with(density) { 10.dp.toPx() }
    val cornerCutoutRadiusPx = with(density) { 12.dp.toPx() }
    val waveHeightPx = with(density) { 4.dp.toPx() }
    val waveWidthPx = with(density) { 10.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(navyBackground)
    ) {
        LottieAnimation(
            composition = confettiComposition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            
            // ACLEDA Logo Header
            Image(
                painter = painterResource(id = R.drawable.img_def_ac),
                contentDescription = "ACLEDA Logo",
                modifier = Modifier.height(50.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(20.dp))

            // Main Receipt Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .drawBehind {
                        val width = size.width
                        val height = size.height

                        val receiptPath = Path().apply {
                            // Start at top-left corner cutout end (on top edge)
                            moveTo(cornerCutoutRadiusPx, 0f)

                            // Top edge with waves
                            val topWaveCount = ((width - (cornerCutoutRadiusPx * 2)) / waveWidthPx).toInt()
                            for (i in 0..topWaveCount) {
                                val x = cornerCutoutRadiusPx + (i * waveWidthPx)
                                val nextX = cornerCutoutRadiusPx + ((i + 1) * waveWidthPx)
                                val midX = (x + nextX) / 2f
                                quadraticTo(midX, -waveHeightPx, nextX, 0f)
                            }
                            lineTo(width - cornerCutoutRadiusPx, 0f)

                            // Top-Right corner cutout bending inwards
                            arcTo(
                                Rect(width - cornerCutoutRadiusPx, -cornerCutoutRadiusPx, width + cornerCutoutRadiusPx, cornerCutoutRadiusPx),
                                180f,
                                -90f,
                                false
                            )

                            // Right edge down to cutout
                            val cutoutY = height * 0.32f
                            lineTo(width, cutoutY - cutoutRadiusPx)
                            arcTo(
                                Rect(width - cutoutRadiusPx, cutoutY - cutoutRadiusPx, width + cutoutRadiusPx, cutoutY + cutoutRadiusPx),
                                270f,
                                -180f,
                                false
                            )
                            lineTo(width, height - cornerCutoutRadiusPx)

                            // Bottom-Right corner cutout bending inwards
                            arcTo(
                                Rect(width - cornerCutoutRadiusPx, height - cornerCutoutRadiusPx, width + cornerCutoutRadiusPx, height + cornerCutoutRadiusPx),
                                270f,
                                -90f,
                                false
                            )

                            // Bottom edge with waves
                            val bottomWaveCount = ((width - (cornerCutoutRadiusPx * 2)) / waveWidthPx).toInt()
                            for (i in 0..bottomWaveCount) {
                                val x = width - cornerCutoutRadiusPx - (i * waveWidthPx)
                                val nextX = width - cornerCutoutRadiusPx - ((i + 1) * waveWidthPx)
                                val midX = (x + nextX) / 2f
                                quadraticTo(midX, height + waveHeightPx, nextX, height)
                            }
                            lineTo(cornerCutoutRadiusPx, height)

                            // Bottom-Left corner cutout bending inwards
                            arcTo(
                                Rect(-cornerCutoutRadiusPx, height - cornerCutoutRadiusPx, cornerCutoutRadiusPx, height + cornerCutoutRadiusPx),
                                0f,
                                -90f,
                                false
                            )

                            // Left edge up to cutout
                            lineTo(0f, cutoutY + cutoutRadiusPx)
                            arcTo(
                                Rect(-cutoutRadiusPx, cutoutY - cutoutRadiusPx, cutoutRadiusPx, cutoutY + cutoutRadiusPx),
                                90f,
                                -180f,
                                false
                            )
                            lineTo(0f, cornerCutoutRadiusPx)

                            // Top-Left corner cutout bending inwards
                            arcTo(
                                Rect(-cornerCutoutRadiusPx, -cornerCutoutRadiusPx, cornerCutoutRadiusPx, cornerCutoutRadiusPx),
                                90f,
                                -90f,
                                false
                            )
                            close()
                        }

                        drawIntoCanvas { canvas ->
                            val frameworkPaint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                                color = android.graphics.Color.TRANSPARENT
                                setShadowLayer(25f, 0f, 8f, android.graphics.Color.argb(45, 0, 0, 0))
                            }
                            canvas.nativeCanvas.drawPath(receiptPath.asAndroidPath(), frameworkPaint)
                        }

                        drawPath(path = receiptPath, color = Color.White)
                    }
                    .padding(vertical = 24.dp, horizontal = 20.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Header: Seal + Text
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GoldSeal(modifier = Modifier.size(68.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "PAYMENT",
                                color = Color(0xFF0F2643),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "SUCCESSFUL",
                                color = Color(0xFFC1770B),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "Thank you for banking with ACLEDA.",
                                color = Color(0xFF64748B),
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))
                    PerforatedLine()
                    Spacer(Modifier.height(16.dp))

                    // Paid to / Amount Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left: Paid to
                        Column(modifier = Modifier.weight(1.2f)) {
                            Text("Paid to", fontSize = 11.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.Medium)
                            Spacer(Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE11D48)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("KHQR", color = Color.White, fontWeight = FontWeight.Black, fontSize = 9.sp)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "CHOUN\nTHEACHUMNITH",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F2643),
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        // Middle: Vertical Divider
                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .width(1.dp)
                                .background(Color(0xFFE2E8F0))
                        )

                        // Right: Amount
                        Column(
                            modifier = Modifier
                                .weight(0.8f)
                                .padding(paddingValues = PaddingValues(start = 16.dp))
                        ) {
                            Text("Amount", fontSize = 11.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.Medium)
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = "0.01 USD",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFDC2626)
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    PerforatedLine()
                    Spacer(Modifier.height(16.dp))

                    // Detail rows
                    DetailRow("Reference ID", "37555d4c", hasCopy = true)
                    DetailRow("Date & Time", "Jul 10, 2026 | 08:06 AM")
                    DetailRow("Paid From", "Choun Theachumnith")
                    DetailRow("Account No.", "000 *** 017 (USD)")
                    DetailRow("Transaction Amount", "-0.01 USD", isRedValue = true)

                    Spacer(Modifier.height(12.dp))
                    PerforatedLine()
                    Spacer(Modifier.height(16.dp))

                    // Footer Address + Stamp
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column(modifier = Modifier.weight(1.3f)) {
                            Text(
                                text = "ACLEDA BANK PLC.",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F2643)
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = "#30, Preah Monivong Blvd., Sangkat Srah Chork,\nKhan Daun Penh, Phnom Penh, Cambodia.",
                                fontSize = 10.sp,
                                color = Color(0xFF64748B),
                                lineHeight = 13.sp
                            )
                        }

                        // Right: Circular stamp and waves
                        Box(
                            modifier = Modifier.weight(0.7f),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            PostmarkStamp()
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}



@Composable
fun GoldSeal(modifier: Modifier = Modifier) {
    val compositionResult = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_def_success))
    val composition = compositionResult.value

    Box(
        modifier = modifier
            .drawBehind {
                val radius = size.minDimension / 2f

                // Outer gold circle
                drawCircle(
                    color = Color(0xFFD97706),
                    radius = radius,
                    style = Stroke(width = 2.dp.toPx())
                )

                // Concentric dashed inner circle
                drawCircle(
                    color = Color(0xFFFBBF24),
                    radius = radius - 6.dp.toPx(),
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f)
                    )
                )

                // Seal center background
                drawCircle(
                    color = Color(0xFFFFFBEB),
                    radius = radius - 8.dp.toPx()
                )

                // Little gold stars decoration
                drawCircle(
                    color = Color(0xFFD97706),
                    radius = radius - 10.dp.toPx(),
                    style = Stroke(width = 0.5.dp.toPx())
                )
            }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            iterations = 1,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun PostmarkStamp() {
    // Wavy postmark lines
    Canvas(modifier = Modifier.size(width = 50.dp, height = 24.dp)) {
        val wavePath = Path()
        val yOffset1 = 4.dp.toPx()
        val yOffset2 = 12.dp.toPx()
        val yOffset3 = 20.dp.toPx()

        for (x in 0..size.width.toInt()) {
            val waveY1 = Math.sin(x * 0.15) * 2.dp.toPx() + yOffset1
            if (x == 0) {
                wavePath.moveTo(0f, waveY1.toFloat())
            } else {
                wavePath.lineTo(x.toFloat(), waveY1.toFloat())
            }
        }

        // Draw line 2
        wavePath.moveTo(0f, yOffset2)
        for (x in 0..size.width.toInt()) {
            val waveY2 = Math.sin(x * 0.15) * 2.dp.toPx() + yOffset2
            if (x == 0) wavePath.moveTo(0f, waveY2.toFloat()) else wavePath.lineTo(x.toFloat(), waveY2.toFloat())
        }

        // Draw line 3
        wavePath.moveTo(0f, yOffset3)
        for (x in 0..size.width.toInt()) {
            val waveY3 = Math.sin(x * 0.15) * 2.dp.toPx() + yOffset3
            if (x == 0) wavePath.moveTo(0f, waveY3.toFloat()) else wavePath.lineTo(x.toFloat(), waveY3.toFloat())
        }

        drawPath(
            path = wavePath,
            color = Color(0xFFD97706).copy(alpha = 0.4f),
            style = Stroke(width = 1.dp.toPx())
        )
    }
}

@Composable
fun PerforatedLine() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 4.dp)
    ) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), 0f)
        drawLine(
            color = Color(0xFFCBD5E1),
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = 1.5.dp.toPx()
        )
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    hasCopy: Boolean = false,
    isRedValue: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label and Value
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF64748B),
            modifier = Modifier.weight(1f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isRedValue) Color(0xFFDC2626) else Color(0xFF0F2643),
                textAlign = TextAlign.End
            )
            if (hasCopy) {
                Spacer(Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy Reference",
                    tint = Color(0xFFD97706),
                    modifier = Modifier
                        .size(14.dp)
                        .clickable { /* Copy to Clipboard placeholder */ }
                )
            }
        }
    }
}



@Preview
@Composable
fun ReceiptPreview() {
    ReceiptScreen()
}