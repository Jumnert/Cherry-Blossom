package com.example.autumntheme.feature.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme
import com.example.autumntheme.ui.theme.CardBrown
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.TextTanGold
import com.example.autumntheme.ui.theme.WarmCream

@Composable
fun DashboardIcon(
    iconRes: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun HeaderText(text: String, modifier: Modifier = Modifier, theme: CardTheme = AutumnTheme) {
    Text(
        color = theme.primaryTextColor,
        modifier = modifier.padding(5.dp),
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )
}
