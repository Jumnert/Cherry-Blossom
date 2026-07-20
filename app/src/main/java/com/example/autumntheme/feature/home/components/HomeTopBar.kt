
package com.example.autumntheme.feature.home.components

import com.example.autumntheme.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme
import com.example.autumntheme.feature.home.DarkBlue
import com.example.autumntheme.ui.theme.DeepBrown
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun HomeTopBar(
    badgeCount: Int,
    modifier: Modifier = Modifier,
    theme: CardTheme = AutumnTheme,
    onQRClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(160.dp)
                    .height(70.dp)
                    .size(50.dp),
                painter = painterResource(theme.logoRes),
                contentDescription = "Acleda Logo",
                contentScale = ContentScale.FillWidth
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* Handle click */ }) {
                BadgedBox(
                    badge = {
                        if (badgeCount > 0) {
                            Badge {
                                Text(text = badgeCount.toString())
                            }
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.ic_def_notification),
                        contentDescription = "Notifications",
                        tint = theme.primaryTextColor
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onQRClick
            ) {
                Image(
                    painter = painterResource(R.drawable.img_def_logo_unnamed),
                    contentDescription = "bakong khqr",
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(6.dp))
                )
            }
        }
    }
}
