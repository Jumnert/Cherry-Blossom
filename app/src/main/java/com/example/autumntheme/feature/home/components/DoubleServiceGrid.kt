package com.example.autumntheme.feature.home.components

import androidx.compose.foundation.background
import com.example.autumntheme.ui.theme.glassEffect
import com.kyant.backdrop.Backdrop
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.R
import com.example.autumntheme.feature.card.CardTheme

@Composable
fun DoubleServiceGrid(
    modifier: Modifier = Modifier,
    theme: CardTheme,
    backdrop: Backdrop? = null
) {
    val iconsList = remember {
        listOf(
            R.drawable.img_acledalogo,
            R.drawable.img_acledalogo,
            R.drawable.img_acledalogo
        )
    }
    val smallIconsList = remember {
        listOf(
            R.drawable.img_acledalogo,
            R.drawable.img_acledalogo,
            R.drawable.img_acledalogo,
            R.drawable.img_acledalogo
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ServiceCategoryCard(
            title = "Public Service",
            modifier = Modifier.weight(1f),
            theme = theme,
            icons = iconsList,
            smallIcons = smallIconsList,
            backdrop = backdrop
        )
        ServiceCategoryCard(
            title = "Other Services",
            modifier = Modifier.weight(1f),
            theme = theme,
            icons = iconsList,
            smallIcons = smallIconsList,
            backdrop = backdrop
        )
    }
}

@Composable
fun ServiceCategoryCard(
    title: String,
    modifier: Modifier = Modifier,
    theme: CardTheme,
    icons: List<Int>,
    smallIcons: List<Int>,
    backdrop: Backdrop? = null
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = title,
                color = theme.primaryTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = theme.primaryTextColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .glassEffect(shape = RoundedCornerShape(24.dp), alpha = 0.15f, tintColor = theme.cardBackgroundColor, accentColor = if (theme.useRomdoulMotif) theme.iconBorderColor else theme.buttonColor, backdrop = backdrop, isTrueGlass = (theme.name == "Glass"), frosted = theme.useFrostedSurface, solid = !theme.useGlassEffect)
                .padding(12.dp)
        ) {
            if (theme.useRomdoulMotif) {
                RomdoulCardTexture(modifier = Modifier.matchParentSize())
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ServiceIconSquare(iconRes = icons.getOrNull(0), modifier = Modifier.weight(1f))
                    ServiceIconSquare(iconRes = icons.getOrNull(1), modifier = Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ServiceIconSquare(iconRes = icons.getOrNull(2), modifier = Modifier.weight(1f))
                    // Small Grid
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                ServiceIconSquare(iconRes = smallIcons.getOrNull(0), modifier = Modifier.weight(1f), shape = 8)
                                ServiceIconSquare(iconRes = smallIcons.getOrNull(1), modifier = Modifier.weight(1f), shape = 8)
                            }
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                ServiceIconSquare(iconRes = smallIcons.getOrNull(2), modifier = Modifier.weight(1f), shape = 8)
                                ServiceIconSquare(iconRes = smallIcons.getOrNull(3), modifier = Modifier.weight(1f), shape = 8)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceIconSquare(
    iconRes: Int?,
    modifier: Modifier = Modifier,
    shape: Int = 12
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(shape.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.7f),
                tint = Color.Unspecified
            )
        }
    }
}
