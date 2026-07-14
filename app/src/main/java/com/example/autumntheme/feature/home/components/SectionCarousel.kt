package com.example.autumntheme.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.autumntheme.feature.home.CardColor
import com.example.autumntheme.feature.home.DarkBlue
import com.example.autumntheme.ui.theme.BorderTan
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.PumpkinOrange
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

data class CarouselSection(val name: String, val iconRes: Int)

@Composable
fun SectionCarousel(
    sections: List<CarouselSection>,
    modifier: Modifier = Modifier,
    onSectionClick: (CarouselSection) -> Unit = {},
    hazeState: HazeState
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
//            .hazeEffect(
//                state = hazeState,
//                style = HazeStyle(
//                    backgroundColor = DeepBrown,
//                    tint = HazeTint(DeepBrown.copy(alpha = 0.5f)),
//                    blurRadius = 30.dp,
//                    noiseFactor = 0.05f
//                )
//            )
            .padding(horizontal = 16.dp)
//            .border(
//                width = 2.dp,
//                color = PumpkinOrange,
//                shape = RoundedCornerShape(16.dp)
//            )
        ,
        colors = CardDefaults.cardColors(containerColor = DeepBrown),
        shape = RoundedCornerShape(16.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        backgroundColor = DeepBrown,
                        tint = HazeTint(DeepBrown.copy(alpha = 0.5f)),
                        blurRadius = 30.dp,
                        noiseFactor = 0.05f
                    )
                ),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(sections) { section ->
                CarouselItem(
                    name = section.name,
                    icon = painterResource(id = section.iconRes),
                    onClick = { onSectionClick(section) },
                    hazeState = hazeState
                )
            }
        }
    }
}

@Composable
fun CarouselItem(
    name: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    hazeState: HazeState
) {
    Column(
        modifier = modifier
            .width(80.dp)
            .clickable { onClick() }
            .padding(vertical = 4.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(BorderTan, CircleShape)
                .clip(RoundedCornerShape(16.dp))
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        backgroundColor = DeepBrown,
                        tint = HazeTint(DeepBrown.copy(alpha = 0.6f)),
                        blurRadius = 30.dp,
                        noiseFactor = 0.05f
                    )
                )
               ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = name,
                modifier = Modifier.size(28.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
