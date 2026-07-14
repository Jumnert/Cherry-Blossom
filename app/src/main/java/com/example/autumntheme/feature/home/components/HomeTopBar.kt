
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.autumntheme.feature.home.DarkBlue
import com.example.autumntheme.ui.theme.DeepBrown
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun HomeTopBar(badgeCount: Int, modifier: Modifier = Modifier, ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.width(200.dp),
                painter = painterResource(R.drawable.ac),
                contentDescription = "Acleda Logo"
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
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "Notifications",
                        tint = DeepBrown
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.unnamed),
                contentDescription = "bakong khqr",
                modifier = Modifier
                    .size(35.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
        }
    }
}
