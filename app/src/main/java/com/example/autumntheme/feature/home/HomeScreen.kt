package com.example.autumntheme.feature.home

import com.example.autumntheme.R

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CarouselItem
import com.example.autumntheme.feature.card.GreetingCard
import com.example.autumntheme.feature.card.PropertyCarousel
import com.example.autumntheme.feature.card.ScreenshotThemeCard
import com.example.autumntheme.feature.card.offercardcarousel
import com.example.autumntheme.feature.card.CardTheme
import com.example.autumntheme.feature.home.components.*
import com.example.autumntheme.feature.qr.QRScannerScreen
import com.example.autumntheme.feature.receipt.ReceiptScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.autumntheme.feature.home.components.OutwardRoundedBottomShape


val DarkBlue1 = Color(0xFF020617)
val DarkBlue = Color(0xFF0F172A)
val Gold = Color(0xFFEAB308)
val LightBlue = Color(0xFF38BDF8)
val CardColor = Color(0xFF1E293B)
val CardColor1 = Color(0xFF334155)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    currentTheme: CardTheme,
    onThemeSelected: (CardTheme) -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToReceipt: () -> Unit
) {
    val sampleCarouselItemse = remember {
        listOf(
            CarouselItem(
                title = "Student in rural area studying at a young age was captured by the moeys team",
                imagePlaceholder = R.drawable.img_def_student_study,
                profileImage = R.drawable.img_def_moeys

            ),
            CarouselItem(
                title = "Second sample property card for checking horizontal swipe behavior",
                imagePlaceholder = R.drawable.img_def_potential_1,
                profileImage = R.drawable.img_def_potential_2
            ),
            CarouselItem(
                title = "Third sample property card to fully test out the dot indicators",
                imagePlaceholder = R.drawable.img_def_forte_1,
                profileImage = R.drawable.img_def_forte_2
            )
        )
    }

    val tourismItems = remember {
        listOf(
            CarouselItem(
                title = "Eco-tourism of the Preah Luang Do...",
                imagePlaceholder = R.drawable.img_def_banner1
            ),
            CarouselItem(
                title = "Explore the beautiful beaches of Sihanoukville",
                imagePlaceholder = R.drawable.img_def_banner2
            ),
            CarouselItem(
                title = "Visit the majestic Angkor Wat temple",
                imagePlaceholder = R.drawable.img_def_banner3
            ),
            CarouselItem(
                title = "Discover the wildlife in Mondulkiri",
                imagePlaceholder = R.drawable.img_def_banner4
            )
        )
    }
    val sampleCarouselItems = remember(currentTheme) {
        listOf(
            CarouselSection("Payments", currentTheme.icPayment),
            CarouselSection("Tuan Chet", currentTheme.icDepartment),
            CarouselSection("Cards", currentTheme.icCard),
            CarouselSection("Scan QR", currentTheme.icScanner),
            CarouselSection("Transfers", currentTheme.icTransfer),
            CarouselSection("Deposits", currentTheme.icDeposit),
            CarouselSection("Loans", currentTheme.icLoan),
            CarouselSection("Quick Cash", currentTheme.icQuickCash)
        )
    }

    val listState = rememberLazyListState()
    val isScrollInProgress by remember { derivedStateOf { listState.isScrollInProgress } }
    val density = LocalDensity.current
    val topBarHeightPx = with(density) { 120.dp.toPx() }

    val greetingIndex = 2

    val greetingAlpha by remember {
        derivedStateOf {
            val firstVisibleIndex = listState.firstVisibleItemIndex
            if (firstVisibleIndex > greetingIndex) {
                0f
            } else if (firstVisibleIndex < greetingIndex) {
                1f
            } else {
                val offset = listState.firstVisibleItemScrollOffset.toFloat()
                val fadeRange = 250f // pixels to fade out completely
                (1f - (offset / fadeRange)).coerceIn(0f, 1f)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = currentTheme.backgroundRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 14.dp)
        ) {
            stickyHeader(key = "sticky_header") {
                HomeTopBar(
                    badgeCount = 0,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .zIndex(10f)
                        .clip(OutwardRoundedBottomShape(cornerRadius = 24.dp)),
                    theme = currentTheme,
                    onQRClick = onNavigateToReceipt
                )
            }

            item(key = "spacer_top") {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item(key = "greeting_card") {
                GreetingCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .graphicsLayer {
                            alpha = greetingAlpha
                        },
                    theme = currentTheme
                )
            }
            item(key = "balance_card") {
                BalanceCard(modifier = Modifier.padding(horizontal = 16.dp), theme = currentTheme)
            }

            item(key = "service_grid") {
                Spacer(modifier = Modifier.height(16.dp))
                ServiceGrid(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    theme = currentTheme,
                    onScanQRClick = onNavigateToScanner
                )
            }
            item(key = "section_carousel") {
                Spacer(modifier = Modifier.height(16.dp))
                SectionCarousel(sections = sampleCarouselItems, theme = currentTheme)
            }

            item(key = "recommended_header") {
                Spacer(modifier = Modifier.height(16.dp))
                HeaderText(text = "Recommended", modifier = Modifier.padding(horizontal = 16.dp), theme = currentTheme)
                ImageCarousel(isParentScrolling = isScrollInProgress)
            }

            item(key = "property_carousel") {
                Spacer(modifier = Modifier.height(16.dp))
                PropertyCarousel(
                    items = tourismItems,
                    isTourism = true,
                    headerTitle = "Cambodia Tourism",
                    theme = currentTheme,
                    isParentScrolling = isScrollInProgress
                )
            }

            item(key = "double_service_grid") {
                Spacer(modifier = Modifier.height(16.dp))
                DoubleServiceGrid(theme = currentTheme)
            }

            item(key = "recent_transactions") {
                Spacer(modifier = Modifier.height(16.dp))
                RecentTransactions(theme = currentTheme)
            }

            item(key = "special_offer") {
                Spacer(modifier = Modifier.height(16.dp))
                HeaderText(text = "Special Offer", modifier = Modifier.padding(horizontal = 16.dp), theme = currentTheme)
                offercardcarousel(theme = currentTheme)
            }
            item(key = "appearance_header") {
                Spacer(modifier = Modifier.height(16.dp))
                HeaderText(text = "Appearance", modifier = Modifier.padding(horizontal = 16.dp), theme = currentTheme)
            }
            item(key = "theme_card") {
                ScreenshotThemeCard(
                    currentTheme = currentTheme,
                    onThemeSelected = onThemeSelected
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        currentTheme = AutumnTheme,
        onThemeSelected = {},
        onNavigateToScanner = {},
        onNavigateToReceipt = {}
    )
}
@Preview(name = "Foldable Screen", device = Devices.PIXEL_9_PRO_FOLD, showSystemUi = true)
@Composable
fun HomeScreenPreview1() {
    HomeScreen(
        currentTheme = AutumnTheme,
        onThemeSelected = {},
        onNavigateToScanner = {},
        onNavigateToReceipt = {}
    )
}