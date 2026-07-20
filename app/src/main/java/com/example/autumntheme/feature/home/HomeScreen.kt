package com.example.autumntheme.feature.home

import com.example.autumntheme.R

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme
import com.example.autumntheme.feature.card.CarouselItem
import com.example.autumntheme.feature.card.GreetingCard
import com.example.autumntheme.feature.card.PropertyCarousel
import com.example.autumntheme.feature.card.ScreenshotThemeCard
import com.example.autumntheme.feature.card.offercardcarousel
import com.example.autumntheme.feature.home.components.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

// 1. Data model for reorderable Home sections
enum class HomeSectionType {
    SPACER_TOP,
    GREETING,
    BALANCE,
    SERVICE_GRID,
    SECTION_CAROUSEL,
    RECOMMENDED,
    PROPERTY_CAROUSEL,
    DOUBLE_SERVICE_GRID,
    RECENT_TRANSACTIONS,
    SPECIAL_OFFER,
    APPEARANCE_HEADER,
    THEME_CARD
}

data class HomeSectionItem(
    val id: String,
    val type: HomeSectionType
)

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
    val haptic = LocalHapticFeedback.current

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

    // 2. Mutable list of sections to allow dynamic reordering
    val homeSections = remember {
        mutableStateListOf(
            HomeSectionItem("spacer_top", HomeSectionType.SPACER_TOP),
            HomeSectionItem("greeting_card", HomeSectionType.GREETING),
            HomeSectionItem("balance_card", HomeSectionType.BALANCE),
            HomeSectionItem("service_grid", HomeSectionType.SERVICE_GRID),
            HomeSectionItem("section_carousel", HomeSectionType.SECTION_CAROUSEL),
            HomeSectionItem("recommended_header", HomeSectionType.RECOMMENDED),
            HomeSectionItem("property_carousel", HomeSectionType.PROPERTY_CAROUSEL),
            HomeSectionItem("double_service_grid", HomeSectionType.DOUBLE_SERVICE_GRID),
            HomeSectionItem("recent_transactions", HomeSectionType.RECENT_TRANSACTIONS),
            HomeSectionItem("special_offer", HomeSectionType.SPECIAL_OFFER),
            HomeSectionItem("appearance_header", HomeSectionType.APPEARANCE_HEADER),
            HomeSectionItem("theme_card", HomeSectionType.THEME_CARD)
        )
    }

    val listState = rememberLazyListState()
    val activeBackdrop = null
    var fontScale by remember { mutableStateOf(1.0f) }
    val EaseOutCubic = remember { CubicBezierEasing(0.215f, 0.610f, 0.355f, 1.0f) }
    val entranceStates = remember { List(13) { Animatable(0f) } }

    // Drag-and-drop state for LazyColumn items
    val dragDropState = rememberLazyListDragDropState(listState) { fromLazyIndex, toLazyIndex ->
        // Sticky header occupies lazy item index 0, so section index = lazyIndex - 1
        val fromSectionIndex = fromLazyIndex - 1
        val toSectionIndex = toLazyIndex - 1

        if (fromSectionIndex in homeSections.indices && toSectionIndex in homeSections.indices) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            val movedItem = homeSections.removeAt(fromSectionIndex)
            homeSections.add(toSectionIndex, movedItem)
        }
    }

    LaunchedEffect(currentTheme) {
        listState.scrollToItem(0)
        entranceStates.forEach { it.snapTo(0f) }
        entranceStates.forEachIndexed { index, animatable ->
            launch {
                delay(index * 60L)
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = EaseOutCubic
                    )
                )
            }
        }
    }

    val isScrollInProgress by remember { derivedStateOf { listState.isScrollInProgress } }

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
                val fadeRange = 250f
                (1f - (offset / fadeRange)).coerceIn(0f, 1f)
            }
        }
    }

    val defaultDensity = LocalDensity.current
    val customDensity = remember(defaultDensity, fontScale) {
        Density(
            density = defaultDensity.density,
            fontScale = defaultDensity.fontScale * fontScale
        )
    }

    CompositionLocalProvider(LocalDensity provides customDensity) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = currentTheme.backgroundRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            if (currentTheme.backgroundScrimColor != Color.Transparent) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(currentTheme.backgroundScrimColor)
                )
            }

            if (currentTheme.name == "Frosted") {
                SnowEffect(modifier = Modifier.fillMaxSize())
            } else if (currentTheme.name == "Autumn") {
                FallingLeavesEffect(
                    modifier = Modifier.fillMaxSize(),
                    leafResId = R.drawable.img_autumn_maple_leaf
                )
            } else if (currentTheme.name == "Matcha") {
                FallingLeavesEffect(
                    modifier = Modifier.fillMaxSize(),
                    leafResId = R.drawable.img_matcha_leaf
                )
            } else if (currentTheme.name == "Lagoon") {
                currentTheme.leafImageRes?.let { leafRes ->
                    FallingLeavesEffect(
                        modifier = Modifier.fillMaxSize(),
                        leafResId = leafRes
                    )
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(dragDropState) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = { offset ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                dragDropState.onDragStart(offset)
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragDropState.onDrag(dragAmount)
                            },
                            onDragEnd = { dragDropState.onDragInterrupted() },
                            onDragCancel = { dragDropState.onDragInterrupted() }
                        )
                    },
                contentPadding = PaddingValues(bottom = 14.dp)
            ) {
                // Fixed Sticky Header (Not draggable)
                stickyHeader(key = "sticky_header") {
                    val progress = entranceStates[0].value
                    HomeTopBar(
                        badgeCount = 0,
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth()
                            .zIndex(10f)
                            .clip(OutwardRoundedBottomShape(cornerRadius = 24.dp))
                            .graphicsLayer {
                                translationY = -80f * (1f - progress)
                            },
                        theme = currentTheme,
                        onQRClick = onNavigateToReceipt
                    )
                }

                // 3. Render reorderable sections dynamically
                itemsIndexed(
                    items = homeSections,
                    key = { _, section -> section.id }
                ) { sectionIndex, section ->
                    val lazyListIndex = sectionIndex + 1 // Offset by 1 for sticky header
                    val isDragging = dragDropState.draggedIndex == lazyListIndex
                    val animProgress = entranceStates[(sectionIndex + 1).coerceAtMost(12)].value

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                translationY = if (isDragging) {
                                    dragDropState.dragOffset
                                } else {
                                    60f * (1f - animProgress)
                                }
                                alpha = if (isDragging) 0.85f else animProgress
                                scaleX = if (isDragging) 1.03f else 1.0f
                                scaleY = if (isDragging) 1.03f else 1.0f
                                shadowElevation = if (isDragging) 16.dp.toPx() else 0f
                            }
                            .animateItem() // Handles smooth repositioning animations
                    ) {
                        when (section.type) {
                            HomeSectionType.SPACER_TOP -> {
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                            HomeSectionType.GREETING -> {
                                GreetingCard(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .graphicsLayer { alpha = greetingAlpha },
                                    theme = currentTheme
                                )
                            }
                            HomeSectionType.BALANCE -> {
                                BalanceCard(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    theme = currentTheme,
                                    backdrop = activeBackdrop
                                )
                            }
                            HomeSectionType.SERVICE_GRID -> {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    ServiceGrid(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        theme = currentTheme,
                                        onScanQRClick = onNavigateToScanner,
                                        backdrop = activeBackdrop
                                    )
                                }
                            }
                            HomeSectionType.SECTION_CAROUSEL -> {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    SectionCarousel(
                                        sections = sampleCarouselItems,
                                        theme = currentTheme,
                                        backdrop = activeBackdrop
                                    )
                                }
                            }
                            HomeSectionType.RECOMMENDED -> {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    HeaderText(
                                        text = "Recommended",
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        theme = currentTheme
                                    )
                                    ImageCarousel(isParentScrolling = isScrollInProgress)
                                }
                            }
                            HomeSectionType.PROPERTY_CAROUSEL -> {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    PropertyCarousel(
                                        items = tourismItems,
                                        isTourism = true,
                                        headerTitle = "Cambodia Tourism",
                                        theme = currentTheme,
                                        isParentScrolling = isScrollInProgress
                                    )
                                }
                            }
                            HomeSectionType.DOUBLE_SERVICE_GRID -> {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    DoubleServiceGrid(theme = currentTheme, backdrop = activeBackdrop)
                                }
                            }
                            HomeSectionType.RECENT_TRANSACTIONS -> {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    RecentTransactions(theme = currentTheme, backdrop = activeBackdrop)
                                }
                            }
                            HomeSectionType.SPECIAL_OFFER -> {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    HeaderText(
                                        text = "Special Offer",
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        theme = currentTheme
                                    )
                                    offercardcarousel(theme = currentTheme)
                                }
                            }
                            HomeSectionType.APPEARANCE_HEADER -> {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    HeaderText(
                                        text = "Appearance",
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        theme = currentTheme
                                    )
                                }
                            }
                            HomeSectionType.THEME_CARD -> {
                                ScreenshotThemeCard(
                                    currentTheme = currentTheme,
                                    onThemeSelected = onThemeSelected,
                                    fontScale = fontScale,
                                    onFontScaleChanged = { fontScale = it }
                                )
                            }
                        }
                    }
                }
            } // end LazyColumn
        } // end outer Box
    } // end CompositionLocalProvider
}

// 4. Drag and drop state management for LazyColumn
class LazyListDragDropState(
    private val listState: LazyListState,
    private val onSwap: (Int, Int) -> Unit
) {
    var draggedIndex by mutableStateOf<Int?>(null)
        private set

    var dragOffset by mutableStateOf(0f)
        private set

    fun onDragStart(offset: Offset) {
        listState.layoutInfo.visibleItemsInfo
            .firstOrNull { item ->
                item.index > 0 && // Protect sticky header (index 0) from being dragged
                        offset.y.toInt() in item.offset..(item.offset + item.size)
            }?.let { item ->
                draggedIndex = item.index
                dragOffset = 0f
            }
    }

    fun onDrag(dragAmount: Offset) {
        val currentDraggedIndex = draggedIndex ?: return
        dragOffset += dragAmount.y

        val draggedItemInfo = listState.layoutInfo.visibleItemsInfo
            .firstOrNull { it.index == currentDraggedIndex } ?: return

        val currentCenterY = draggedItemInfo.offset + (draggedItemInfo.size / 2f) + dragOffset

        listState.layoutInfo.visibleItemsInfo
            .firstOrNull { item ->
                if (item.index == currentDraggedIndex || item.index == 0) return@firstOrNull false
                currentCenterY.toInt() in item.offset..(item.offset + item.size)
            }?.let { targetItem ->
                onSwap(currentDraggedIndex, targetItem.index)
                draggedIndex = targetItem.index
                dragOffset = 0f
            }
    }

    fun onDragInterrupted() {
        draggedIndex = null
        dragOffset = 0f
    }
}

@Composable
fun rememberLazyListDragDropState(
    listState: LazyListState,
    onSwap: (Int, Int) -> Unit
): LazyListDragDropState {
    return remember(listState) { LazyListDragDropState(listState, onSwap) }
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

data class SnowParticle(
    var x: Float,
    var y: Float,
    val speed: Float,
    val radius: Float,
    val drift: Float
)

@Composable
fun SnowEffect(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "snow")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "snow_progress"
    )

    val random = remember { Random() }
    val particleCount = 75
    val particles = remember {
        List(particleCount) {
            SnowParticle(
                x = random.nextFloat(),
                y = random.nextFloat(),
                speed = 0.15f + random.nextFloat() * 0.25f,
                radius = 3f + random.nextFloat() * 8f,
                drift = (random.nextFloat() - 0.5f) * 0.1f
            )
        }
    }

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val tick = progress

        particles.forEach { p ->
            var currentY = (p.y + p.speed * tick) % 1.0f
            if (currentY < 0f) currentY += 1.0f
            val currentX = (p.x + p.drift * tick) % 1.0f

            drawCircle(
                color = Color.White.copy(alpha = 0.7f),
                radius = p.radius,
                center = Offset(currentX * width, currentY * height)
            )
        }
    }
}

data class LeafParticle(
    var x: Float,
    var y: Float,
    val speed: Float,
    val size: Float,
    val drift: Float,
    val rotationSpeed: Float,
    val initialRotation: Float
)

@Composable
fun FallingLeavesEffect(modifier: Modifier = Modifier, leafResId: Int) {
    val infiniteTransition = rememberInfiniteTransition(label = "leaves")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "leaves_progress"
    )

    val random = remember { Random() }
    val particleCount = 12
    val particles = remember {
        List(particleCount) {
            LeafParticle(
                x = random.nextFloat(),
                y = random.nextFloat(),
                speed = 0.15f + random.nextFloat() * 0.15f,
                size = 45f + random.nextFloat() * 30f,
                drift = (random.nextFloat() - 0.5f) * 0.03f,
                rotationSpeed = 60f + random.nextFloat() * 90f,
                initialRotation = random.nextFloat() * 360f
            )
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()
        val painter = painterResource(id = leafResId)

        particles.forEach { p ->
            val tick = progress
            var currentY = (p.y + p.speed * tick) % 1.0f
            if (currentY < 0f) currentY += 1.0f
            val currentX = (p.x + p.drift * tick) % 1.0f
            val currentRotation = p.initialRotation + p.rotationSpeed * tick

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(p.size.dp)
                    .graphicsLayer {
                        translationX = currentX * widthPx
                        translationY = currentY * heightPx
                        rotationZ = currentRotation
                        alpha = 0.8f
                    }
            )
        }
    }
}
