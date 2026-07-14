package com.example.autumntheme.feature.home.components

import com.example.autumntheme.R
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.ui.theme.AmberGold
import com.example.autumntheme.ui.theme.BurntOrange
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.PumpkinOrange
import com.example.autumntheme.ui.theme.WarmCream
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun BalanceCard(modifier: Modifier = Modifier, hazeState: HazeState) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            )
        )
    }

    // Outer Box allows us to place overlapping items (like the leaves on the corners)
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // 1. The Main Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp) // Adds safe spacing so overlapping leaves aren't clipped by outer screens
                .clip(RoundedCornerShape(20.dp))
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        backgroundColor = DeepBrown,
                        tint = HazeTint(DeepBrown.copy(alpha = 0.5f)),
                        blurRadius = 30.dp,
                        noiseFactor = 0.05f
                    )
                )
        ) {
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
                    val strokeWidth6 = 14f
                    val strokeWidth8 = 14f
                    val strokeWidth12 = 14f

                    Canvas(modifier = Modifier.size(90.dp)) {
                        val progress = animatedProgress.value

                        drawArc(
                            color = BurntOrange.copy(alpha = 0.1f),
                            startAngle = 0f,
                            sweepAngle = 360f * progress,
                            useCenter = false,
                            style = Stroke(width = strokeWidth6)
                        )

                        drawArc(
                            color = PumpkinOrange,
                            startAngle = -150f + (50f * progress),
                            sweepAngle = 260f * progress,
                            useCenter = false,
                            style = Stroke(width = strokeWidth8, cap = StrokeCap.Round)
                        )

                        drawArc(
                            color = AmberGold,
                            startAngle = 120f + (50f * progress),
                            sweepAngle = 20f * progress,
                            useCenter = false,
                            style = Stroke(width = strokeWidth12, cap = StrokeCap.Round)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        DashboardIcon(
                            iconRes = R.drawable.ic_wallet1,
                            modifier = Modifier.size(45.dp)
                        )
                        Text(
                            text = "Accounts",
                            color = WarmCream,
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
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 16.sp
                        )
                        Spacer(Modifier.width(5.dp))
                        DashboardIcon(
                            iconRes = R.drawable.ic_eye,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    BalanceRow(currency = "៛", amount = "២៣២,២៣៣", color = PumpkinOrange)
                    BalanceRow(currency = "$", amount = "29388", color = AmberGold)
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.img_mapleleaf),
            contentDescription = "Left Maple Leaf",
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.TopStart)
                .offset(x = (-10).dp, y = (-10).dp)
                .rotate(50f)
        )

        Image(
            painter = painterResource(id = R.drawable.img_mapleleaf),
            contentDescription = "Right Maple Leaf",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopEnd)
                .offset(x = (10).dp, y = (-2).dp)
                .rotate(-45f) // Rotates clockwise
        )
    }
}

@Composable
fun BalanceRow(currency: String, amount: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = amount,
            color = WarmCream,
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