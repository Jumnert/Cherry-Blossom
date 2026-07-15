package com.example.autumntheme.feature.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.R
import com.example.autumntheme.feature.card.CardTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

data class NavItemData(val title: String, val icon: ImageVector, val hasBadge: Boolean = false)

@Composable
fun HomeBottomBar(
    isVisible: Boolean,
    hazeState: HazeState,
    theme: CardTheme,
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {}
) {
    val items = remember {
        listOf(
            NavItemData("Home", Icons.Default.Home),
            NavItemData("Favorites", Icons.Default.FavoriteBorder),
            NavItemData("LiveChat", Icons.Default.ChatBubbleOutline, hasBadge = true),
            NavItemData("Menu", Icons.Default.Menu)
        )
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(35.dp))
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        backgroundColor = theme.cardBackgroundColor,
                        tint = HazeTint(theme.cardBackgroundColor.copy(alpha = 0.5f)),
                        blurRadius = 30.dp,
                        noiseFactor = 0.05f
                    )
                )
                .background(theme.cardBackgroundColor.copy(alpha = 0.8f))
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val selected = selectedIndex == index
                    
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(35.dp))
                                .background(
                                    if (selected) Color.White.copy(alpha = 0.2f)
                                    else Color.Transparent
                                )
                                .clickable { onItemSelected(index) }
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (selected) Color(0xFFEAB308) else Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.title,
                                color = if (selected) Color(0xFFEAB308) else Color.White,
                                fontSize = 11.sp,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        
                        if (item.hasBadge) {
                            Text(
                                text = "BETA",
                                color = Color(0xFFEAB308),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 2.dp, end = 4.dp)
                            )
                        }
                    }
                }

                // Right special icon (ACLEDA Logo)
                Image(
                    painter = painterResource(id = R.drawable.img_def_ac),
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun FloatingQRButton(
    isVisible: Boolean,
    theme: CardTheme,
    onClick: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it + 200 }),
        exit = slideOutVertically(targetOffsetY = { it + 200 }),
        modifier = Modifier.padding(bottom = 30.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF1E40AF), // Deep Blue
                            Color(0xFF1E3A8A)
                        )
                    )
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                    .clip(CircleShape)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = theme.icScanner),
                    contentDescription = "Scan QR",
                    tint = Color(0xFFEAB308),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
