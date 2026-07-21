package com.example.autumntheme.feature.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme
import com.example.autumntheme.ui.theme.glassEffect
import com.kyant.backdrop.Backdrop

// 1. Service Item Data Model
enum class ServiceSize { LARGE, SMALL }

data class ServiceItemData(
    val id: String,
    val label: String,
    val size: ServiceSize,
    val getIcon: (CardTheme) -> Int,
    val onClick: (() -> Unit)? = null
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServiceGrid(
    modifier: Modifier = Modifier,
    theme: CardTheme = AutumnTheme,
    onScanQRClick: () -> Unit = {},
    backdrop: Backdrop? = null
) {
    val haptic = LocalHapticFeedback.current

    val items = remember {
        mutableStateListOf(
            ServiceItemData("payments", "Payments", ServiceSize.LARGE, { it.icPayment }),
            ServiceItemData("topup", "Mobile Top-up", ServiceSize.LARGE, { it.icTopup }),
            ServiceItemData("cards", "Cards", ServiceSize.SMALL, { it.icCard }),
            ServiceItemData("scan", "Scan QR", ServiceSize.SMALL, { it.icScanner }, onScanQRClick),
            ServiceItemData("transfers", "Transfers", ServiceSize.SMALL, { it.icTransfer }),
            ServiceItemData("deposits", "Deposits", ServiceSize.SMALL, { it.icDeposit }),
            ServiceItemData("loans", "Loans", ServiceSize.SMALL, { it.icLoan }),
            ServiceItemData("quickcash", "Quick Cash", ServiceSize.SMALL, { it.icQuickCash })
        )
    }

    val gridState = rememberLazyGridState()
    val dragDropState = rememberDragDropState(gridState) { fromIndex, toIndex ->
        // Trigger haptic vibration on item swap
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)

        // Swap items while transforming sizes to fit target slots
        val fromItem = items[fromIndex]
        val toItem = items[toIndex]

        items[fromIndex] = toItem.copy(size = fromItem.size)
        items[toIndex] = fromItem.copy(size = toItem.size)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        state = gridState,
        modifier = modifier
            .fillMaxWidth()
            .height(310.dp)
            .pointerInput(dragDropState) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { offset ->
                        // Trigger haptic vibration on drag pickup
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
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item.id },
            span = { _, item ->
                GridItemSpan(if (item.size == ServiceSize.LARGE) 3 else 2)
            }
        ) { index, item ->
            val isDragging = dragDropState.draggedIndex == index

            val itemModifier = Modifier
                .gridElement(
                    isDragging = isDragging,
                    dragOffset = dragDropState.dragOffset
                )
                .animateItem() // Handles smooth spatial animations on swap & resize

            if (item.size == ServiceSize.LARGE) {
                ServiceItemLarge(
                    iconRes = item.getIcon(theme),
                    label = item.label,
                    modifier = itemModifier,
                    theme = theme,
                    backdrop = backdrop,
                    onClick = item.onClick ?: {}
                )
            } else {
                ServiceItemSmall(
                    iconRes = item.getIcon(theme),
                    label = item.label,
                    modifier = itemModifier,
                    theme = theme,
                    backdrop = backdrop,
                    onClick = item.onClick ?: {}
                )
            }
        }
    }
}

// 2. Drag Visual Effects Modifier
fun Modifier.gridElement(isDragging: Boolean, dragOffset: Offset): Modifier = this
    .graphicsLayer {
        alpha = if (isDragging) 0.75f else 1.0f
        scaleX = if (isDragging) 1.06f else 1.0f
        scaleY = if (isDragging) 1.06f else 1.0f
        shadowElevation = if (isDragging) 12.dp.toPx() else 0f
    }
    .offset {
        if (isDragging) IntOffset(dragOffset.x.toInt(), dragOffset.y.toInt())
        else IntOffset.Zero
    }

// 3. UI Card Components
@Composable
fun ServiceItemLarge(
    iconRes: Int,
    label: String,
    modifier: Modifier = Modifier,
    theme: CardTheme,
    backdrop: Backdrop? = null,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(70.dp)
            .glassEffect(
                shape = RoundedCornerShape(16.dp),
                alpha = 0.2f,
                tintColor = theme.cardBackgroundColor,
                accentColor = if (theme.useRomdoulMotif) theme.iconBorderColor else theme.buttonColor,
                backdrop = backdrop,
                isTrueGlass = (theme.name == "Glass"),
                frosted = theme.useFrostedSurface,
                solid = !theme.useGlassEffect
            )
            .clickable { onClick() }
    ) {
        if (theme.useRomdoulMotif) {
            RomdoulCardTexture(modifier = Modifier.matchParentSize())
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DashboardIcon(
                iconRes = iconRes,
                modifier = Modifier
                    .size(45.dp)
                    .graphicsLayer {
                        val iconScale = if (theme.name.contains("Gold") || theme.useRomdoulMotif || theme.name == "Emblem Professional") 1.2f else 1f
                        scaleX = iconScale
                        scaleY = iconScale
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = theme.secondaryTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ServiceItemSmall(
    iconRes: Int,
    label: String,
    modifier: Modifier = Modifier,
    theme: CardTheme,
    backdrop: Backdrop? = null,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .glassEffect(
                shape = RoundedCornerShape(16.dp),
                alpha = 0.2f,
                tintColor = theme.cardBackgroundColor,
                accentColor = if (theme.useRomdoulMotif) theme.iconBorderColor else theme.buttonColor,
                backdrop = backdrop,
                isTrueGlass = (theme.name == "Glass"),
                frosted = theme.useFrostedSurface,
                solid = !theme.useGlassEffect
            )
            .clickable { onClick() }
    ) {
        if (theme.useRomdoulMotif) {
            RomdoulCardTexture(modifier = Modifier.matchParentSize())
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DashboardIcon(
                iconRes = iconRes,
                modifier = Modifier
                    .size(55.dp)
                    .graphicsLayer {
                        val iconScale = if (theme.name.contains("Gold") || theme.useRomdoulMotif || theme.name == "Emblem Professional") 1.24f else 1f
                        scaleX = iconScale
                        scaleY = iconScale
                    }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = theme.secondaryTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

// 4. Drag and Drop Pointer & Hit Testing State Logic
class DragDropState(
    private val gridState: LazyGridState,
    private val onSwap: (Int, Int) -> Unit
) {
    var draggedIndex by mutableStateOf<Int?>(null)
        private set

    var dragOffset by mutableStateOf(Offset.Zero)
        private set

    fun onDragStart(offset: Offset) {
        gridState.layoutInfo.visibleItemsInfo
            .firstOrNull { item ->
                val xIn = offset.x.toInt() in item.offset.x..(item.offset.x + item.size.width)
                val yIn = offset.y.toInt() in item.offset.y..(item.offset.y + item.size.height)
                xIn && yIn
            }?.let { item ->
                draggedIndex = item.index
            }
    }

    fun onDrag(amount: Offset) {
        val currentDraggedIndex = draggedIndex ?: return
        dragOffset += amount

        val draggedItemInfo = gridState.layoutInfo.visibleItemsInfo
            .firstOrNull { it.index == currentDraggedIndex } ?: return

        val currentCenter = Offset(
            x = draggedItemInfo.offset.x + draggedItemInfo.size.width / 2f + dragOffset.x,
            y = draggedItemInfo.offset.y + draggedItemInfo.size.height / 2f + dragOffset.y
        )

        gridState.layoutInfo.visibleItemsInfo
            .firstOrNull { item ->
                if (item.index == currentDraggedIndex) return@firstOrNull false
                val xIn = currentCenter.x.toInt() in item.offset.x..(item.offset.x + item.size.width)
                val yIn = currentCenter.y.toInt() in item.offset.y..(item.offset.y + item.size.height)
                xIn && yIn
            }?.let { targetItem ->
                onSwap(currentDraggedIndex, targetItem.index)
                draggedIndex = targetItem.index
                dragOffset = Offset.Zero
            }
    }

    fun onDragInterrupted() {
        draggedIndex = null
        dragOffset = Offset.Zero
    }
}

@Composable
fun rememberDragDropState(
    gridState: LazyGridState,
    onSwap: (Int, Int) -> Unit
): DragDropState {
    return remember(gridState) { DragDropState(gridState, onSwap) }
}
