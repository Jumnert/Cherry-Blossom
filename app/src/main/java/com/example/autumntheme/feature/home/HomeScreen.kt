package com.example.autumntheme.feature.home

import com.example.autumntheme.R

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.autumntheme.feature.card.CarouselItem
import com.example.autumntheme.feature.card.DarkBlueTheme
import com.example.autumntheme.feature.card.GreetingCard
import com.example.autumntheme.feature.card.PropertyCarousel
import com.example.autumntheme.feature.card.ScreenshotThemeCard
import com.example.autumntheme.feature.card.offercardcarousel
import com.example.autumntheme.feature.home.components.*
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import com.example.autumntheme.feature.home.components.OutwardRoundedBottomShape


val DarkBlue1 = Color(0xFF020617)
val DarkBlue = Color(0xFF0F172A)
val Gold = Color(0xFFEAB308)
val LightBlue = Color(0xFF38BDF8)
val CardColor = Color(0xFF1E293B)
val CardColor1 = Color(0xFF334155)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    var currentTheme by remember { mutableStateOf(DarkBlueTheme) }
    val sampleCarouselItemse = remember {
        listOf(
            CarouselItem(
                title = "Student in rural area studying at a young age was captured by the moeys team",
                imagePlaceholder = R.drawable.img_studentstudy,
                profileImage = R.drawable.img_moeys

            ),
            CarouselItem(
                title = "Second sample property card for checking horizontal swipe behavior",
                imagePlaceholder = R.drawable.img_potiential,
                profileImage = R.drawable.img_potential
            ),
            CarouselItem(
                title = "Third sample property card to fully test out the dot indicators",
                imagePlaceholder = R.drawable.img_forte,
                profileImage = R.drawable.forte
            ),
            CarouselItem(
                title = "Third sample property card to fully test out the dot indicators",
                imagePlaceholder = R.drawable.img_forte,
                profileImage = R.drawable.forte
            ),
            CarouselItem(
                title = "Third sample property card to fully test out the dot indicators",
                imagePlaceholder = R.drawable.img_forte,
                profileImage = R.drawable.forte
            )
        )
    }
    val sampleCarouselItems = remember {
        listOf(
            CarouselSection("Payments", R.drawable.ic_school),
            CarouselSection("Tuan Chet", R.drawable.ic_department),
            CarouselSection("Cards", R.drawable.ic_exchange1),
            CarouselSection("Scan QR", R.drawable.ic_scanner1),
            CarouselSection("Transfers", R.drawable.ic_transfer1),
            CarouselSection("Deposits", R.drawable.ic_deposit1),
            CarouselSection("Loans", R.drawable.ic_loan1),
            CarouselSection("Quick Cash", R.drawable.ic_card1)
        )
    }

    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val topBarHeightPx = with(density) { 120.dp.toPx() }

    val greetingIndex = 2

    val greetingAlpha by remember {
        derivedStateOf {
            val info = listState.layoutInfo.visibleItemsInfo.find { it.index == greetingIndex }
            if (info == null) {
                if (listState.firstVisibleItemIndex > greetingIndex) 0f else 1f
            } else {
                val itemTop = info.offset.toFloat()
                val itemSize = info.size.toFloat()

                val hiddenAmount = (topBarHeightPx - itemTop).coerceAtLeast(0f)
                (1f - (hiddenAmount / itemSize)).coerceIn(0f, 1f)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val hazeState = remember { HazeState() }


//
//        VideoBackground(
//            videoResId = R.raw.bg_autumn,
//            modifier = Modifier
//                .fillMaxSize()
//                .hazeSource(state = hazeState)
//        )
//
        Image(
            painter = painterResource(id = R.drawable.img_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState),
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 14.dp)
        ) {
            stickyHeader {
                HomeTopBar(
                    badgeCount = 0,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .zIndex(10f)
                        .clip(OutwardRoundedBottomShape(cornerRadius = 24.dp))
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                GreetingCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .graphicsLayer {
                            alpha = greetingAlpha
                        }
                )
            }
            item {
                BalanceCard(modifier = Modifier.padding(horizontal = 16.dp), hazeState = hazeState,)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                ServiceGrid(hazeState = hazeState, modifier = Modifier.padding(horizontal = 16.dp))
            }
//            item {
//                HorizontalDivider(thickness = 2.dp, modifier = Modifier
//                    .padding(horizontal = 16.dp, vertical = 8.dp))
//            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionCarousel(sections = sampleCarouselItems, hazeState = hazeState,)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                HeaderText(text = "Recommended", modifier = Modifier.padding(horizontal = 16.dp), )
                ImageCarousel()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                HeaderText(text = "Recommended Place", modifier = Modifier.padding(horizontal = 16.dp))
                PropertyCarousel(items = sampleCarouselItemse)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                HeaderText(text = "Special Offer", modifier = Modifier.padding(horizontal = 16.dp))
                offercardcarousel(hazeState = hazeState)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HeaderText(text = "Appearance", modifier = Modifier.padding(horizontal = 16.dp))
            }
            item {
                ScreenshotThemeCard(
                    currentTheme = currentTheme,
                    onThemeSelected = { newTheme ->
                        currentTheme = newTheme
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}