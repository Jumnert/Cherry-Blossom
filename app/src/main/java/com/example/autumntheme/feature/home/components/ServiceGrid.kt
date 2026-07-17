package com.example.autumntheme.feature.home.components

import com.example.autumntheme.ui.theme.glassEffect
import com.kyant.backdrop.Backdrop
import androidx.compose.foundation.clickable
import com.example.autumntheme.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme

@Composable
fun ServiceGrid(
    modifier: Modifier = Modifier,
    theme: CardTheme = AutumnTheme,
    onScanQRClick: () -> Unit = {},
    backdrop: Backdrop? = null
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ServiceItemLarge(
                iconRes = theme.icPayment,
                label = "Payments",
                modifier = Modifier.weight(1f),
                theme = theme,
                backdrop = backdrop
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemLarge(
                iconRes = theme.icTopup,
                label = "Mobile Top-up",
                modifier = Modifier.weight(1.2f),
                theme = theme,
                backdrop = backdrop
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ServiceItemSmall(
                iconRes = theme.icCard,
                label = "Cards",
                modifier = Modifier.weight(1f),
                theme = theme,
                backdrop = backdrop
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemSmall(
                iconRes = theme.icScanner,
                label = "Scan QR",
                modifier = Modifier.weight(1f),
                theme = theme,
                onClick = onScanQRClick,
                backdrop = backdrop
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemSmall(
                iconRes = theme.icTransfer,
                label = "Transfers",
                modifier = Modifier.weight(1f),
                theme = theme,
                backdrop = backdrop
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ServiceItemSmall(
                iconRes = theme.icDeposit,
                label = "Deposits",
                modifier = Modifier.weight(1f),
                theme = theme,
                backdrop = backdrop
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemSmall(
                iconRes = theme.icLoan,
                label = "Loans",
                modifier = Modifier.weight(1f),
                theme = theme,
                backdrop = backdrop
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemSmall(
                iconRes = theme.icQuickCash,
                label = "Quick Cash",
                modifier = Modifier.weight(1f),
                theme = theme,
                backdrop = backdrop
            )
        }
    }
}

@Composable
fun ServiceItemLarge(
    iconRes: Int,
    label: String,
    modifier: Modifier = Modifier,
    theme: CardTheme,
    backdrop: Backdrop? = null
) {
    Box(
        modifier = modifier
            .height(70.dp)
            .glassEffect(
                shape = RoundedCornerShape(16.dp),
                alpha = 0.2f,
                tintColor = theme.cardBackgroundColor,
                accentColor = theme.buttonColor,
                backdrop = backdrop,
                isTrueGlass = (theme.name == "Glass")
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            DashboardIcon(iconRes = iconRes, modifier = Modifier.size(45.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = theme.secondaryTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ServiceItemSmall(
    iconRes: Int,
    label: String,
    modifier: Modifier = Modifier,
    theme: CardTheme,
    onClick: () -> Unit = {},
    backdrop: Backdrop? = null
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .glassEffect(
                shape = RoundedCornerShape(16.dp),
                alpha = 0.2f,
                tintColor = theme.cardBackgroundColor,
                accentColor = theme.buttonColor,
                backdrop = backdrop,
                isTrueGlass = (theme.name == "Glass")
            )
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DashboardIcon(iconRes = iconRes, modifier = Modifier.size(55.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = theme.secondaryTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}
