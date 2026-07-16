package com.example.autumntheme.feature.card

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.R
import kotlinx.coroutines.delay

@Composable
fun PropertyCard(
    modifier: Modifier = Modifier,
    imagePlaceholder: Int,
    title: String,
    profileImage: Int? = null,
    isTourism: Boolean = false
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imagePlaceholder),
                contentDescription = "Property Preview",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.2f),
                                Color.Black.copy(alpha = 0.6f),
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isTourism) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.White.copy(alpha = 0.3f), CircleShape)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    } else if (profileImage != null) {
                        Image(
                            painter = painterResource(id = profileImage),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(36.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = title,
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Immutable
data class CarouselItem(
    val title: String,
    val imagePlaceholder: Int,
    val profileImage: Int? = null
)

@Composable
fun PropertyCarousel(
    modifier: Modifier = Modifier,
    items: List<CarouselItem>,
    isTourism: Boolean = false,
    headerTitle: String? = null,
    theme: CardTheme = AutumnTheme,
    isParentScrolling: Boolean = false
) {
    if (items.isEmpty()) return

    val pagerState = rememberPagerState { items.size }

    LaunchedEffect(isParentScrolling, pagerState.isScrollInProgress) {
        if (!isParentScrolling && !pagerState.isScrollInProgress) {
            while (true) {
                delay(5000)
                if (!isParentScrolling && !pagerState.isScrollInProgress) {
                    val nextPage = (pagerState.currentPage + 1) % items.size
                    pagerState.animateScrollToPage(nextPage, animationSpec = tween(800))
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        if (headerTitle != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = headerTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = theme.primaryTextColor
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "More",
                    tint = theme.primaryTextColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 48.dp),
            pageSpacing = 12.dp
        ) { page ->
            val item = items[page]
            PropertyCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                imagePlaceholder = item.imagePlaceholder,
                title = item.title,
                profileImage = item.profileImage,
                isTourism = isTourism
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(items.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .height(6.dp)
                        .width(if (isSelected) 16.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color.White else Color.White.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun previe12() {
    PropertyCard(
        modifier = Modifier.width(300.dp).height(180.dp),
        title = "Student in rural area studying at young age was captured by the moeys team",
        imagePlaceholder = R.drawable.img_def_student_study,
        profileImage = R.drawable.img_def_moeys
    )
}