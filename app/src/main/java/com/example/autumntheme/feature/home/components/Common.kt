package com.example.autumntheme.feature.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.ui.theme.CardBrown
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.TextTanGold
import com.example.autumntheme.ui.theme.WarmCream

@Composable
fun DashboardIcon(
    iconRes: Int,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = modifier,
        tint = Color.Unspecified
    )
}

@Composable
fun HeaderText(text: String, modifier: Modifier = Modifier) {
    Text(
        color = CardBrown,
        modifier = modifier.padding(5.dp),
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )
}
