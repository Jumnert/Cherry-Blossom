package com.example.autumntheme.feature.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Palette ───────────────────────────────────────────────────────────────

object AutumnGlass {
    val Amber   = Color(0xFFE51A4C)
    val Pumpkin = Color(0xFFF25270)
    val Espresso = Color(0xFFFFA3A8)
    val Cream   = Color(0xFFFFA3A8)
}

// ─── Base Modifier ─────────────────────────────────────────────────────────

fun Modifier.glassEffect(
    tintColor: Color = Color.White,
    tintAlpha: Float = 0.12f,
    borderAlpha: Float = 0.3f,
    cornerRadius: Dp = 16.dp
): Modifier = this
    .clip(RoundedCornerShape(cornerRadius))
    .background(tintColor.copy(alpha = tintAlpha))
    .background(
        Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.18f),
                Color.White.copy(alpha = 0.0f),
            )
        )
    )
    .background(
        Brush.linearGradient(
            0.0f to Color.White.copy(alpha = 0.10f),
            0.4f to Color.White.copy(alpha = 0.0f),
            1.0f to Color.White.copy(alpha = 0.05f),
        )
    )
    .border(
        width = 0.5.dp,
        color = tintColor.copy(alpha = borderAlpha),
        shape = RoundedCornerShape(cornerRadius)
    )
    .border(
        width = 1.dp,
        brush = Brush.linearGradient(
            0.0f to Color.White.copy(alpha = 0.55f),
            0.3f to Color.White.copy(alpha = 0.15f),
            1.0f to Color.White.copy(alpha = 0.0f),
        ),
        shape = RoundedCornerShape(cornerRadius)
    )
    .border(
        width = 1.5.dp,
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                tintColor.copy(alpha = 0.25f),
            )
        ),
        shape = RoundedCornerShape(cornerRadius)
    )

// ─── GlassCard Base ────────────────────────────────────────────────────────

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    tintColor: Color = Color.White,
    tintAlpha: Float = 0.12f,
    borderAlpha: Float = 0.3f,
    cornerRadius: Dp = 16.dp,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .glassEffect(tintColor, tintAlpha, borderAlpha, cornerRadius)
            .padding(contentPadding),
        content = content
    )
}

// ─── BalanceCard ───────────────────────────────────────────────────────────

@Composable
fun BalanceCard(
    label: String,
    amount: String,
    currency: String = "USD",
    subLabel: String = "",
    modifier: Modifier = Modifier,
    tintColor: Color = AutumnGlass.Amber,
    tintAlpha: Float = 0.15f,
    borderAlpha: Float = 0.35f,
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        tintColor = tintColor,
        tintAlpha = tintAlpha,
        borderAlpha = borderAlpha,
        cornerRadius = 20.dp,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = currency,
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 12.sp
                )
            }
            Text(
                text = amount,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )
            if (subLabel.isNotEmpty()) {
                Text(
                    text = subLabel,
                    color = Color.White.copy(alpha = 0.45f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

// ─── StatCard (small) ──────────────────────────────────────────────────────

@Composable
fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    tintColor: Color = Color.White,
    tintAlpha: Float = 0.10f,
    borderAlpha: Float = 0.2f,
) {
    GlassCard(
        modifier = modifier,
        tintColor = tintColor,
        tintAlpha = tintAlpha,
        borderAlpha = borderAlpha,
        cornerRadius = 16.dp,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.55f),
                fontSize = 12.sp
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ─── Demo Screen ───────────────────────────────────────────────────────────

@Composable
fun GlassCardDemoScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFC1770B),
                        Color(0xFFD2691E),
                        Color(0xFFFFA3A8)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Autumn Wallet",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Good morning, Jumnert",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Main balance — amber tint
            BalanceCard(
                label = "Total Balance",
                amount = "$2,450.00",
                currency = "USD",
                subLabel = "≈ 10,004,500 ៛",
                tintColor = AutumnGlass.Amber,
                tintAlpha = 0.15f,
                borderAlpha = 0.35f
            )

            // Savings — pumpkin tint
            BalanceCard(
                label = "Savings",
                amount = "$840.00",
                currency = "USD",
                subLabel = "Monthly goal: $1,000",
                tintColor = AutumnGlass.Pumpkin,
                tintAlpha = 0.18f,
                borderAlpha = 0.3f
            )

            // Stat row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    label = "Income",
                    value = "$3,200",
                    modifier = Modifier.weight(1f),
                    tintColor = Color.White,
                    tintAlpha = 0.10f
                )
                StatCard(
                    label = "Expenses",
                    value = "$750",
                    modifier = Modifier.weight(1f),
                    tintColor = AutumnGlass.Espresso,
                    tintAlpha = 0.4f,
                    borderAlpha = 0.15f
                )
                StatCard(
                    label = "Exchange",
                    value = "4,100 ៛",
                    modifier = Modifier.weight(1f),
                    tintColor = AutumnGlass.Amber,
                    tintAlpha = 0.20f
                )
            }

            // Dark glass card
            BalanceCard(
                label = "Khmer Riel",
                amount = "10,004,500 ៛",
                currency = "KHR",
                subLabel = "≈ $2,450.00",
                tintColor = AutumnGlass.Espresso,
                tintAlpha = 0.5f,
                borderAlpha = 0.12f
            )
        }
    }
}

// ─── Previews ──────────────────────────────────────────────────────────────

@Preview(showSystemUi = true)
@Composable
fun GlassCardDemoPreview() {
    GlassCardDemoScreen()
}