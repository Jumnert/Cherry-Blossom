package com.example.autumntheme.feature.home.components

import com.example.autumntheme.R
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import com.example.autumntheme.ui.theme.glassEffect
import com.kyant.backdrop.Backdrop
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme

@Composable
fun BalanceCard(
    modifier: Modifier = Modifier,
    theme: CardTheme = AutumnTheme,
    backdrop: Backdrop? = null
) {
    var hasAnimated by rememberSaveable { mutableStateOf(false) }
    val animatedProgress = remember { Animatable(if (hasAnimated) 1f else 0f) }
    val context = LocalContext.current
    val leafPainter = theme.leafImageRes?.let { leafRes ->
        val leafRequest = remember(leafRes) {
            ImageRequest.Builder(context)
                .data(leafRes)
                .crossfade(true)
                .allowHardware(true)
                .build()
        }
        rememberAsyncImagePainter(model = leafRequest)
    }

    LaunchedEffect(Unit) {
        if (!hasAnimated) {
            animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
            )
            hasAnimated = true
        }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .glassEffect(
                    shape = RoundedCornerShape(20.dp),
                    alpha = 0.25f,
                    tintColor = theme.cardBackgroundColor,
                    accentColor = if (theme.useRomdoulMotif) theme.iconBorderColor else theme.buttonColor,
                    backdrop = backdrop,
                    isTrueGlass = (theme.name == "Glass"),
                    frosted = theme.useFrostedSurface,
                    solid = !theme.useGlassEffect
                )
        ) {
            if (theme.useRomdoulMotif) {
                RomdoulCardTexture(modifier = Modifier.matchParentSize())
            }
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val strokeWidth = theme.strokeWidth
                    val progress = animatedProgress.value

                    Canvas(modifier = Modifier.size(theme.arcCanvasSize)) {
                        drawArc(
                            color = theme.arcTrackColor ?: theme.buttonColor.copy(alpha = 0.1f),
                            startAngle = 0f,
                            sweepAngle = 360f * progress,
                            useCenter = false,
                            style = Stroke(width = strokeWidth)
                        )
                        drawArc(
                            color = theme.arcPrimaryColor ?: theme.buttonColor,
                            startAngle = theme.arcPrimaryStartAngle + (50f * progress),
                            sweepAngle = theme.arcPrimarySweepAngle * progress,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = theme.arcSecondaryColor ?: theme.iconBorderColor,
                            startAngle = theme.arcSecondaryStartAngle + (50f * progress),
                            sweepAngle = theme.arcSecondarySweepAngle * progress,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        DashboardIcon(
                            iconRes = theme.icWallet,
                            modifier = Modifier
                                .size(45.dp)
                                .graphicsLayer {
                                    val iconScale = if (theme.name.contains("Gold") || theme.useRomdoulMotif || theme.name == "Emblem Professional") 1.29f else 1f
                                    scaleX = iconScale
                                    scaleY = iconScale
                                }
                        )
                        Text(
                            text = "Accounts",
                            color = theme.secondaryTextColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Balances",
                            fontWeight = FontWeight.Normal,
                            color = theme.secondaryTextColor.copy(alpha = 0.8f),
                            fontSize = 16.sp
                        )
                        if (theme.showBalanceVisibilityIcon) {
                            Spacer(Modifier.width(5.dp))
                            DashboardIcon(iconRes = R.drawable.ic_def_eye, modifier = Modifier.size(24.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    BalanceRow(currency = "៛", amount = "២៣២,២៣៣", color = theme.buttonColor, textColor = theme.secondaryTextColor)
                    BalanceRow(currency = "$", amount = "29388", color = theme.iconBorderColor, textColor = theme.secondaryTextColor)
                }
            }
        }

        if (leafPainter != null && theme.name != "Professional" && theme.name != "Monochrome" && theme.name != "Gold Premium" && theme.name != "Halloween") {
            Image(
                painter = leafPainter,
                contentDescription = "Left Leaf",
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.TopStart)
                    .offset(x = (-10).dp, y = (-10).dp)
                    .rotate(50f)
            )
            Image(
                painter = leafPainter,
                contentDescription = "Right Leaf",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (10).dp, y = (-2).dp)
                    .rotate(-45f)
            )
        }
    }
}

@Composable
fun BalanceRow(currency: String, amount: String, color: Color, textColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = amount,
            color = textColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = currency,
            color = color,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
