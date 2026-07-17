    package com.example.autumntheme.feature.home

    import com.example.autumntheme.R

    import androidx.compose.foundation.ExperimentalFoundationApi
    import androidx.compose.foundation.Image
    import com.kyant.backdrop.backdrops.rememberLayerBackdrop
    import com.kyant.backdrop.backdrops.layerBackdrop
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.rememberLazyListState
    import androidx.compose.animation.core.Animatable
    import androidx.compose.animation.core.CubicBezierEasing
    import androidx.compose.animation.core.*
    import androidx.compose.foundation.Canvas
    import androidx.compose.foundation.layout.BoxWithConstraints
    import androidx.compose.ui.geometry.Offset
    import androidx.compose.ui.graphics.drawscope.withTransform
    import java.util.Random
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.CompositionLocalProvider
    import androidx.compose.ui.unit.Density
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.delay
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
    import androidx.compose.ui.draw.blur
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
        val backdrop = rememberLayerBackdrop()
        val activeBackdrop = backdrop
        var fontScale by remember { mutableStateOf(1.0f) }
        val EaseOutCubic = remember { CubicBezierEasing(0.215f, 0.610f, 0.355f, 1.0f) }
        val entranceStates = remember {
            List(13) { Animatable(0f) }
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

        val defaultDensity = LocalDensity.current
        val customDensity = remember(defaultDensity, fontScale) {
            Density(
                density = defaultDensity.density,
                fontScale = defaultDensity.fontScale * fontScale
            )
        }

        CompositionLocalProvider(LocalDensity provides customDensity) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = currentTheme.backgroundRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .let { if (currentTheme.name == "Glass") it.blur(6.dp) else it }
                        .layerBackdrop(backdrop),
                    contentScale = ContentScale.Crop
                )

                if (currentTheme.name == "Frosted") {
                    SnowEffect(modifier = Modifier.fillMaxSize())
                } else if (currentTheme.name == "Autumn") {
                    FallingLeavesEffect(modifier = Modifier.fillMaxSize(), leafResId = R.drawable.img_autumn_maple_leaf)
                } else if (currentTheme.name == "Matcha") {
                    FallingLeavesEffect(modifier = Modifier.fillMaxSize(), leafResId = R.drawable.img_matcha_leaf)
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 14.dp)
                ) {
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

                item(key = "spacer_top") {
                    val progress = entranceStates[1].value
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            }
                    )
                }
                item(key = "greeting_card") {
                    val progress = entranceStates[2].value
                    GreetingCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress * greetingAlpha
                            },
                        theme = currentTheme
                    )
                }
                item(key = "balance_card") {
                    val progress = entranceStates[3].value
                    BalanceCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            },
                        theme = currentTheme,
                        backdrop = activeBackdrop
                    )
                }

                item(key = "service_grid") {
                    val progress = entranceStates[4].value
                    Column(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            }
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        ServiceGrid(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            theme = currentTheme,
                            onScanQRClick = onNavigateToScanner,
                            backdrop = activeBackdrop
                        )
                    }
                }
                item(key = "section_carousel") {
                    val progress = entranceStates[5].value
                    Column(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            }
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionCarousel(sections = sampleCarouselItems, theme = currentTheme, backdrop = activeBackdrop)
                    }
                }

                item(key = "recommended_header") {
                    val progress = entranceStates[6].value
                    Column(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            }
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HeaderText(text = "Recommended", modifier = Modifier.padding(horizontal = 16.dp), theme = currentTheme)
                        ImageCarousel(isParentScrolling = isScrollInProgress)
                    }
                }

                item(key = "property_carousel") {
                    val progress = entranceStates[7].value
                    Column(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            }
                    ) {
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

                item(key = "double_service_grid") {
                    val progress = entranceStates[8].value
                    Column(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            }
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        DoubleServiceGrid(theme = currentTheme, backdrop = activeBackdrop)
                    }
                }

                item(key = "recent_transactions") {
                    val progress = entranceStates[9].value
                    Column(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                              }
                      ) {
                          Spacer(modifier = Modifier.height(16.dp))
                          RecentTransactions(theme = currentTheme, backdrop = activeBackdrop)
                      }
                  }

                item(key = "special_offer") {
                    val progress = entranceStates[10].value
                    Column(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            }
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HeaderText(text = "Special Offer", modifier = Modifier.padding(horizontal = 16.dp), theme = currentTheme)
                        offercardcarousel(theme = currentTheme)
                    }
                }
                item(key = "appearance_header") {
                    val progress = entranceStates[11].value
                    Column(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            }
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HeaderText(text = "Appearance", modifier = Modifier.padding(horizontal = 16.dp), theme = currentTheme)
                    }
                }
                item(key = "theme_card") {
                    val progress = entranceStates[12].value
                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = 60f * (1f - progress)
                                alpha = progress
                            }
                    ) {
                        ScreenshotThemeCard(
                             currentTheme = currentTheme,
                             onThemeSelected = onThemeSelected,
                             fontScale = fontScale,
                             onFontScaleChanged = { fontScale = it }
                         )
                     }
                 }
            }  // end LazyColumn
            }  // end outer Box
        }  // end CompositionLocalProvider(LocalDensity)
    }  // end HomeScreen



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