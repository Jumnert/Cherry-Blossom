package com.example.autumntheme.feature.home.components

import com.example.autumntheme.R

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.example.autumntheme.ui.theme.DeepBrown
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun ImageCarousel(
    modifier: Modifier = Modifier
) {
    val imageList = remember {
        listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4,
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
        )
    }

    val pagerState = rememberPagerState { imageList.size }

    // Auto-scroll logic with safety check for user interaction
    LaunchedEffect(pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            while (true) {
                delay(4000) // Wait 4 seconds between scrolls
                if (!pagerState.isScrollInProgress) {
                    val nextPage = (pagerState.currentPage + 1) % imageList.size
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = tween(durationMillis = 800)
                    )
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)

        ) { page ->
            AsyncImage(
                model = imageList[page],
                contentDescription = "Banner Image $page",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                ).absoluteValue
                        val fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        val scaleValue = lerp(0.9f, 1.0f, fraction)

                        scaleY = scaleValue
                        scaleX = scaleValue
                        alpha = lerp(0.8f, 1.0f, fraction)
                    }
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Dot Indicators
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(imageList.size) { index ->
                val isSelected = pagerState.currentPage == index

                Box(
                    modifier = Modifier
                        .size(if (isSelected) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            color = if (isSelected) Color(0xFFD4AF37) else Color.White.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}

