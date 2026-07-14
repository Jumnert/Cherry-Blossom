package com.example.autumntheme.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.example.autumntheme.R

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.feature.home.CardColor
import com.example.autumntheme.ui.theme.AmberGold
import com.example.autumntheme.ui.theme.BorderTan
import com.example.autumntheme.ui.theme.BurntOrange
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.PumpkinOrange
import com.example.autumntheme.ui.theme.WarmCream
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun ServiceGrid(
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ServiceItemLarge(
                iconRes = R.drawable.ic_payment1,
                label = "Payments",
                hazeState = hazeState,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemLarge(
                iconRes = R.drawable.ic_topup1,
                label = "Mobile Top-up",
                hazeState = hazeState,
                modifier = Modifier.weight(1.2f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ServiceItemSmall(
                iconRes = R.drawable.ic_card1,
                label = "Cards",
                hazeState = hazeState,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemSmall(
                iconRes = R.drawable.ic_scanner1,
                label = "Scan QR",
                hazeState = hazeState,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemSmall(
                iconRes = R.drawable.ic_transfer1,
                label = "Transfers",
                hazeState = hazeState,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ServiceItemSmall(
                iconRes = R.drawable.ic_deposit1,
                label = "Deposits",
                hazeState = hazeState,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemSmall(
                iconRes = R.drawable.ic_loan1,
                label = "Loans",
                hazeState = hazeState,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            ServiceItemSmall(
                iconRes = R.drawable.ic_quickcash1,
                label = "Quick Cash",
                hazeState = hazeState,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ServiceItemLarge(
    iconRes: Int,
    label: String,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(70.dp)
            .clip(RoundedCornerShape(16.dp))
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = DeepBrown,
                    tint = HazeTint(DeepBrown.copy(alpha = 0.5f)),
                    blurRadius = 30.dp,
                    noiseFactor = 0.05f
                )
            )
//            .border(
//                width = 1.dp,
//                color = PumpkinOrange.copy(alpha = 0.6f),
//                shape = RoundedCornerShape(16.dp)
//            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            DashboardIcon(
                iconRes = iconRes,
                modifier = Modifier.size(45.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = WarmCream,
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
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = DeepBrown,
                    tint = HazeTint(DeepBrown.copy(alpha = 0.5f)),
                    blurRadius = 30.dp,
                    noiseFactor = 0.05f
                )
            )
//            .border(
//                width = 2.dp,
//                color = BurntOrange.copy(alpha = 0.6f),
//                shape = RoundedCornerShape(16.dp)
//            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DashboardIcon(
                iconRes = iconRes,
                modifier = Modifier.size(55.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = WarmCream,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}