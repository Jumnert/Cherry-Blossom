package com.example.autumntheme.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.example.autumntheme.ui.theme.glassEffect
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme
import com.example.autumntheme.feature.home.CardColor
import com.example.autumntheme.feature.home.DarkBlue
import com.example.autumntheme.ui.theme.BorderTan
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.PumpkinOrange

@Immutable
data class CarouselSection(val name: String, val iconRes: Int)

@Composable
fun SectionCarousel(
    sections: List<CarouselSection>,
    modifier: Modifier = Modifier,
    onSectionClick: (CarouselSection) -> Unit = {},
    theme: CardTheme = AutumnTheme
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .glassEffect(shape = RoundedCornerShape(16.dp), alpha = 0.12f, tintColor = theme.cardBackgroundColor, accentColor = theme.buttonColor),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(sections) { section ->
                CarouselItem(
                    name = section.name,
                    iconRes = section.iconRes,
                    onClick = { onSectionClick(section) },
                    theme = theme
                )
            }
        }
    }
}

@Composable
fun CarouselItem(
    name: String,
    iconRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    theme: CardTheme
) {
    Column(
        modifier = modifier
            .width(80.dp)
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(theme.sheetContentColor, CircleShape)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            color = theme.secondaryTextColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
