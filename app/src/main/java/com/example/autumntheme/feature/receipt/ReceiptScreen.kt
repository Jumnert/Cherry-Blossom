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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
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
import com.example.autumntheme.feature.card.CardTheme
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.ProfessionalTheme

@Composable
fun ReceiptScreen(theme: CardTheme = ProfessionalTheme) {
    val confetti = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti_1)).value
    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(theme.backgroundRes), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.62f)))
        LottieAnimation(confetti, iterations = 1, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding().padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.img_ac_logo_white),
                contentDescription = "ACLEDA logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(150.dp).height(62.dp)
            )
            Spacer(Modifier.height(10.dp))
            ReferenceTicketReceiptV2(theme)
        }
    }
}

@Composable
private fun ReferenceTicketReceiptV2(theme: CardTheme) {
    val success = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_def_success)).value
    Box(
        modifier = Modifier.fillMaxWidth().widthIn(max = 440.dp).padding(horizontal = 28.dp, vertical = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawBehind {
                    val corner = 28.dp.toPx()
                    val notch = 14.dp.toPx()
                    val notchY = size.height * 0.42f
                    drawRoundRect(Color.White, cornerRadius = CornerRadius(corner, corner))
                    drawCircle(Color.Transparent, notch, Offset(0f, notchY), blendMode = BlendMode.Clear)
                    drawCircle(Color.Transparent, notch, Offset(size.width, notchY), blendMode = BlendMode.Clear)
                    val scallop = 14.dp.toPx()
                    var x = scallop
                    while (x < size.width) {
                        drawCircle(Color.Transparent, scallop, Offset(x, size.height + scallop * 0.62f), blendMode = BlendMode.Clear)
                        x += scallop * 2f
                    }
                }
                .padding(horizontal = 20.dp, vertical = 28.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFF173B68)).padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieAnimation(success, iterations = 1, modifier = Modifier.fillMaxSize())
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text("Payment", fontSize = 21.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF123D70))
                        Text("Successful", fontSize = 21.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF123D70))
                        Text("Transaction completed", fontSize = 11.sp, color = Color(0xFF6B7280))
                    }
                }
                Spacer(Modifier.height(18.dp))
                Surface(color = Color(0xFFF7F7F7), shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(horizontal = 16.dp, vertical = 13.dp)) {
                        Text("Transaction KHQR to", fontSize = 14.sp, color = Color(0xFF222222))
                        Spacer(Modifier.height(4.dp))
                        Text("CHOUN THEACHUMNITH", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF233A5B))
                        Spacer(Modifier.height(4.dp))
                        Text("0.01 $", fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFB91C1C))
                    }
                }
                Spacer(Modifier.height(14.dp))
                PerforatedLine()
                Spacer(Modifier.height(12.dp))
                Surface(color = Color(0xFFF7F7F7), shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                        ReceiptInfoRow("External Txn Ref", "37555d4c")
                        Spacer(Modifier.height(10.dp))
                        ReceiptInfoRow("Date", "Jul 10, 2026 | 08:06 AM")
                        Spacer(Modifier.height(10.dp))
                        PerforatedLine()
                        Spacer(Modifier.height(10.dp))
                        ReceiptInfoRow("Paid From", "Choun\nTheachumnith")
                        Spacer(Modifier.height(10.dp))
                        ReceiptInfoRow("Account No.", "000 *** 017 (USD)")
                        Spacer(Modifier.height(10.dp))
                        ReceiptInfoRow("Debit Amount", "-0.01 USD", valueColor = Color(0xFFB91C1C))
                        Spacer(Modifier.height(10.dp))
                        PerforatedLine()
                    }
                }
            }
        }
    }
}

@Composable
private fun ReceiptInfoRow(label: String, value: String, valueColor: Color = Color(0xFF161616)) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Text("$label :", fontSize = 13.sp, color = Color(0xFF222222), modifier = Modifier.width(104.dp))
        Text(value, fontSize = 13.sp, lineHeight = 18.sp, color = valueColor, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ReferenceTicketReceipt(theme: CardTheme) {
    Box(
        modifier = Modifier.fillMaxWidth().widthIn(max = 440.dp).padding(horizontal = 28.dp)
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
            .drawBehind {
                val radius = 14.dp.toPx()
                val notchRadius = 18.dp.toPx()
                val notchY = size.height * 0.61f
                drawRoundRect(Color.White, cornerRadius = CornerRadius(radius, radius))
                drawCircle(Color.Transparent, notchRadius, Offset(0f, notchY), blendMode = BlendMode.Clear)
                drawCircle(Color.Transparent, notchRadius, Offset(size.width, notchY), blendMode = BlendMode.Clear)
                val scallopRadius = 17.dp.toPx()
                var x = scallopRadius
                while (x < size.width) {
                    drawCircle(Color.Transparent, scallopRadius, Offset(x, size.height), blendMode = BlendMode.Clear)
                    x += scallopRadius * 2f
                }
            }.padding(horizontal = 28.dp, vertical = 28.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.CheckCircle, "Payment successful", tint = theme.buttonColor, modifier = Modifier.size(58.dp))
                Spacer(Modifier.height(12.dp))
                Text("Thank you", fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Color(0xFF161616))
                Spacer(Modifier.height(6.dp))
                Text("Your payment of 0.01 USD has been processed\nsuccessfully.", fontSize = 12.sp, lineHeight = 18.sp, color = Color(0xFF5D5D5D), textAlign = TextAlign.Center)
            }
            Spacer(Modifier.height(26.dp)); PerforatedLine(); Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) { TicketLabel("TICKET ID"); Spacer(Modifier.height(5.dp)); Text("37555d4c", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF171717)) }
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) { TicketLabel("AMOUNT"); Spacer(Modifier.height(5.dp)); Text("0.01 USD", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF171717)) }
            }
            Spacer(Modifier.height(22.dp)); TicketLabel("DATE & TIME"); Spacer(Modifier.height(5.dp))
            Text("Jul 10, 2026 | 08:06 AM", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF171717))
            Spacer(Modifier.height(24.dp))
            Surface(color = Color(0xFFF0F5FF), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(28.dp).clip(CircleShape).background(Color(0xFFE11D48)), contentAlignment = Alignment.Center) { Text("KHQR", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color.White) }
                    Spacer(Modifier.width(10.dp))
                    Column { Text("Paid to", fontSize = 12.sp, color = Color(0xFF5D6470)); Text("CHOUN THEACHUMNITH", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF171717)) }
                }
            }
            Spacer(Modifier.height(26.dp)); PerforatedLine(); Spacer(Modifier.height(28.dp))
            ReceiptBarcode()
            Text("37555D4C0000000001", fontSize = 8.sp, color = Color(0xFF5D6470), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(14.dp))
        }
    }
}

@Composable
private fun TicketLabel(text: String) {
    Text(text, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color(0xFF6E7278))
}

@Composable
private fun LegacyReceiptScreen(theme: CardTheme = ProfessionalTheme) {
    val scrollState = rememberScrollState()

    val confettiCompositionResult = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val confettiComposition = confettiCompositionResult.value

    val density = LocalDensity.current
    val cornerRadiusPx = with(density) { 20.dp.toPx() }
    val cutoutRadiusPx = with(density) { 16.dp.toPx() }
    val cornerCutoutRadiusPx = with(density) { 12.dp.toPx() }
    val waveHeightPx = with(density) { 14.dp.toPx() }
    val waveWidthPx = with(density) { 44.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(theme.backgroundRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.58f))
        )
        LottieAnimation(
            composition = confettiComposition,
            iterations = 1,
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
            
            Spacer(Modifier.height(18.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 440.dp)
                    .padding(horizontal = 24.dp)
                    .drawBehind {
                        val width = size.width
                        val height = size.height

                        val receiptPath = Path().apply {
                            // Start at top-left corner cutout end (on top edge)
                            moveTo(cornerCutoutRadiusPx, 0f)

                            // Clean straight top like a paper ticket.
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
                    // Ticket-style success header
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GoldSeal(modifier = Modifier.size(68.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Payment Successful", color = Color(0xFF0F2643), fontSize = 21.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Thank you for banking with ACLEDA.", color = Color(0xFF64748B), fontSize = 12.sp)
                    }

                    Spacer(Modifier.height(20.dp))
                    PerforatedLine()
                    Spacer(Modifier.height(16.dp))

                    // Ticket ID / Amount Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("TICKET ID", fontSize = 10.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
                            Spacer(Modifier.height(6.dp))
                            Text("37555d4c", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F2643))
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text("AMOUNT", fontSize = 10.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
                            Spacer(Modifier.height(6.dp))
                            Text("0.01 USD", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFDC2626))
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    Text("DATE & TIME", fontSize = 10.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(5.dp))
                    Text("Jul 10, 2026 | 08:06 AM", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F2643))
                    Spacer(Modifier.height(16.dp))
                    Surface(
                        color = Color(0xFFF0F5FF),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.size(30.dp).clip(CircleShape).background(Color(0xFFE11D48)), contentAlignment = Alignment.Center) {
                                Text("KHQR", color = Color.White, fontWeight = FontWeight.Black, fontSize = 8.sp)
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text("Paid to", fontSize = 11.sp, color = Color(0xFF64748B))
                                Text("CHOUN THEACHUMNITH", fontSize = 13.sp, color = Color(0xFF0F2643), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(Modifier.height(18.dp))
                    PerforatedLine()
                    Spacer(Modifier.height(16.dp))

                    ReceiptBarcode()
                    Text(
                        text = "37555D4C0000000001",
                        fontSize = 8.sp,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}



@Composable
fun ReceiptBarcode() {
    val bars = listOf(2, 1, 3, 1, 2, 4, 1, 2, 1, 3, 2, 1, 4, 2, 1, 3, 1, 2, 4, 1, 2, 3, 1, 4)
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .padding(horizontal = 40.dp)
    ) {
        val unitWidth = size.width / (bars.sum() + bars.size).toFloat()
        var x = 0f
        bars.forEach { units ->
            val width = unitWidth * units
            drawRect(
                color = Color(0xFF111111),
                topLeft = Offset(x, 0f),
                size = Size(width, size.height - 10.dp.toPx())
            )
            x += width + unitWidth
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
        val yOffset1 = 8.dp.toPx()
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
