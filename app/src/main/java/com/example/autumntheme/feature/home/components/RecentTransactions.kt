package com.example.autumntheme.feature.home.components

import androidx.compose.foundation.background
import com.example.autumntheme.ui.theme.glassEffect
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme

@Immutable
data class Transaction(val name: String, val initials: String, val color: Color)

@Composable
fun RecentTransactions(
    modifier: Modifier = Modifier,
    theme: CardTheme = AutumnTheme
) {
    val transactions = remember {
        listOf(
            Transaction("TELA...", "TS", Color(0xFF22C55E)),
            Transaction("THEAC...", "TC", Color(0xFF10B981)),
            Transaction("REM P...", "RP", Color(0xFF14B8A6)),
            Transaction("CHOU...", "CT", Color(0xFF0D9488)),
            Transaction("Rithy E...", "RE", Color(0xFF059669)),
            Transaction("TEST...", "TT", Color(0xFF065F46))
        )
    }

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(
            text = "Recent Transactions",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = theme.primaryTextColor,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .glassEffect(shape = RoundedCornerShape(24.dp), alpha = 0.15f, tintColor = theme.cardBackgroundColor, accentColor = theme.buttonColor)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(transactions) { transaction ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(60.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(transaction.color),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = transaction.initials,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = transaction.name,
                                color = theme.secondaryTextColor,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
