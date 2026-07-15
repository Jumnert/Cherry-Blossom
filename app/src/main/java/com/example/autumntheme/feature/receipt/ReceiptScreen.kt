package com.example.autumntheme.feature.receipt

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asAndroidPath
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
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFC1770B), Color(0xFFD2691E), Color.White)
    )

    val compositionResult = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_def_success))
    val composition = compositionResult.value

    val confettiCompositionResult = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val confettiComposition = confettiCompositionResult.value

    val density = LocalDensity.current
    val cornerRadiusPx = with(density) { 24.dp.toPx() }
    val cutoutRadiusPx = with(density) { 12.dp.toPx() }
    val pillarWidthPx = with(density) { 20.dp.toPx() }
    val pillarHeightPx = with(density) { 22.dp.toPx() }
    val waveHeightPx = with(density) { 6.dp.toPx() }
    val waveCount = 14

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
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
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_def_ac),
                contentDescription = null,
                modifier = Modifier.height(55.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(45.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 45.dp)
                        .drawBehind {
                            val width = size.width
                            val height = size.height
                            val waveWidth = (width - (pillarWidthPx * 2)) / waveCount

                            val receiptPath = Path().apply {
                                moveTo(0f, cornerRadiusPx)
                                arcTo(Rect(0f, 0f, cornerRadiusPx * 2, cornerRadiusPx * 2), 180f, 90f, false)
                                lineTo(width - cornerRadiusPx, 0f)
                                arcTo(Rect(width - cornerRadiusPx * 2, 0f, width, cornerRadiusPx * 2), 270f, 90f, false)

                                lineTo(width, height * 0.55f - cutoutRadiusPx)
                                arcTo(Rect(width - cutoutRadiusPx, height * 0.55f - cutoutRadiusPx, width + cutoutRadiusPx, height * 0.55f + cutoutRadiusPx), 270f, -180f, false)

                                lineTo(width, height - pillarHeightPx)
                                lineTo(width - pillarWidthPx, height - pillarHeightPx)
                                lineTo(width - pillarWidthPx, height - waveHeightPx)

                                for (i in 0 until waveCount) {
                                    val currentX = width - pillarWidthPx - (i * waveWidth)
                                    val nextX = width - pillarWidthPx - ((i + 1) * waveWidth)
                                    val midX = (currentX + nextX) / 2f
                                    quadraticTo(midX, height, nextX, height - waveHeightPx)
                                }

                                lineTo(pillarWidthPx, height - pillarHeightPx)
                                lineTo(0f, height - pillarHeightPx)

                                lineTo(0f, height * 0.55f + cutoutRadiusPx)
                                arcTo(Rect(-cutoutRadiusPx, height * 0.55f - cutoutRadiusPx, cutoutRadiusPx, height * 0.55f + cutoutRadiusPx), 90f, -180f, false)
                                close()
                            }

                            drawIntoCanvas { canvas ->
                                val frameworkPaint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                                    color = android.graphics.Color.TRANSPARENT
                                    setShadowLayer(35f, 0f, 12f, android.graphics.Color.argb(38, 0, 0, 0))
                                }
                                canvas.nativeCanvas.drawPath(receiptPath.asAndroidPath(), frameworkPaint)
                            }

                            drawPath(path = receiptPath, color = Color.White)
                        }
                        .clip(ReceiptShape(cornerRadiusPx, cutoutRadiusPx, pillarWidthPx, pillarHeightPx, waveCount, waveHeightPx))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 55.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Success",
                            color = Color(0xFF0F172A),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(Modifier.height(20.dp))

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFF8FAFC),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE11D48)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("KHQR", color = Color.White, fontWeight = FontWeight.Black, fontSize = 11.sp)
                                }

                                Spacer(Modifier.width(14.dp))

                                Column {
                                    Text("Transaction KHQR to", fontSize = 13.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
                                    Text("CHOUN THEACHUMNITH", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                                    Text("0.01 $", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFE11D48))
                                }
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        PerforatedLine()

                        Spacer(Modifier.height(20.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("External Txn Ref : 37555d4c", fontSize = 13.sp, color = Color(0xFF475569), fontWeight = FontWeight.Medium)
                                Icon(Icons.Default.KeyboardArrowUp, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))
                            }

                            Spacer(Modifier.height(12.dp))

                            DetailRow("Date", "Jul 10, 2026 | 08:06 AM")
                            DetailRow("Paid From", "Choun Theachumnith")
                            DetailRow("Account No.", "000 *** 017 (USD)")
                            DetailRow("Debit Amount", "-0.01 USD", isRed = true)

                            Spacer(Modifier.height(24.dp))

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, Color(0xFFE2E8F0), CircleShape)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(22.dp))
                                }
                            }
                        }

                        Spacer(Modifier.height(35.dp))
                    }
                }

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(85.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, isRed: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.weight(1f))
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = if (isRed) Color(0xFFDC2626) else Color(0xFF1E293B), textAlign = TextAlign.End, modifier = Modifier.weight(1.5f))
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

fun ReceiptShape(
    cornerRadiusPx: Float,
    cutoutRadiusPx: Float,
    pillarWidthPx: Float,
    pillarHeightPx: Float,
    waveCount: Int,
    waveHeightPx: Float
) = GenericShape { size, _ ->
    val width = size.width
    val height = size.height
    val waveWidth = (width - (pillarWidthPx * 2)) / waveCount

    moveTo(0f, cornerRadiusPx)
    arcTo(Rect(0f, 0f, cornerRadiusPx * 2, cornerRadiusPx * 2), 180f, 90f, false)
    lineTo(width - cornerRadiusPx, 0f)
    arcTo(Rect(width - cornerRadiusPx * 2, 0f, width, cornerRadiusPx * 2), 270f, 90f, false)

    lineTo(width, height * 0.55f - cutoutRadiusPx)
    arcTo(Rect(width - cutoutRadiusPx, height * 0.55f - cutoutRadiusPx, width + cutoutRadiusPx, height * 0.55f + cutoutRadiusPx), 270f, -180f, false)

    lineTo(width, height - pillarHeightPx)
    lineTo(width - pillarWidthPx, height - pillarHeightPx)
    lineTo(width - pillarWidthPx, height - waveHeightPx)

    for (i in 0 until waveCount) {
        val currentX = width - pillarWidthPx - (i * waveWidth)
        val nextX = width - pillarWidthPx - ((i + 1) * waveWidth)
        val midX = (currentX + nextX) / 2f
        quadraticTo(midX, height, nextX, height - waveHeightPx)
    }

    lineTo(pillarWidthPx, height - pillarHeightPx)
    lineTo(0f, height - pillarHeightPx)

    lineTo(0f, height * 0.85f + cutoutRadiusPx)
    arcTo(Rect(-cutoutRadiusPx, height * 0.55f - cutoutRadiusPx, cutoutRadiusPx, height * 0.55f + cutoutRadiusPx), 90f, -180f, false)
    close()
}

@Preview
@Composable
fun ReceiptPreview() {
    ReceiptScreen()
}