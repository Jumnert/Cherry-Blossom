package com.example.autumntheme.dragdrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

data class DragItem(
    val id: Int,
    val name: String,
    val color: Color
)

data class GridPosition(
    val index: Int,
    val x: Dp,
    val y: Dp,
    val width: Dp,
    val height: Dp,
    val isSquare: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                DragDropGridScreen()
            }
        }
    }
}

@Composable
fun DragDropGridScreen() {
    val items = remember {
        mutableStateListOf(
            DragItem(1, "Item 1", Color(0xFF143C6D)),
            DragItem(2, "Item 2", Color(0xFF1E5AA8)),
            DragItem(3, "Item 3", Color(0xFF7B1FA2)),
            DragItem(4, "Item 4", Color(0xFF00897B)),
            DragItem(5, "Item 5", Color(0xFFFF8F00)),
            DragItem(6, "Item 6", Color(0xFFD84315)),
            DragItem(7, "Item 7", Color(0xFF3949AB)),
            DragItem(8, "Item 8", Color(0xFF546E7A))
        )
    }

    /*
     * Row 1: two short rectangles
     * Row 2: three items
     * Row 3: three items
     */
    val gridPositions = listOf(
        GridPosition(
            index = 0,
            x = 4.dp,
            y = 0.dp,
            width = 150.dp,
            height = 76.dp
        ),
        GridPosition(
            index = 1,
            x = 166.dp,
            y = 0.dp,
            width = 150.dp,
            height = 76.dp
        ),

        GridPosition(
            index = 2,
            x = 4.dp,
            y = 88.dp,
            width = 96.dp,
            height = 96.dp
        ),
        GridPosition(
            index = 3,
            x = 112.dp,
            y = 88.dp,
            width = 96.dp,
            height = 96.dp
        ),
        GridPosition(
            index = 4,
            x = 220.dp,
            y = 88.dp,
            width = 96.dp,
            height = 96.dp,
            isSquare = true
        ),

        GridPosition(
            index = 5,
            x = 4.dp,
            y = 196.dp,
            width = 96.dp,
            height = 96.dp
        ),
        GridPosition(
            index = 6,
            x = 112.dp,
            y = 196.dp,
            width = 96.dp,
            height = 96.dp
        ),
        GridPosition(
            index = 7,
            x = 220.dp,
            y = 196.dp,
            width = 96.dp,
            height = 96.dp
        )
    )

    val slotBounds = remember {
        mutableStateMapOf<Int, Rect>()
    }

    var draggingIndex by remember {
        mutableStateOf(-1)
    }

    var hoveredIndex by remember {
        mutableStateOf(-1)
    }

    var dragX by remember {
        mutableStateOf(0f)
    }

    var dragY by remember {
        mutableStateOf(0f)
    }

    var message by remember {
        mutableStateOf("Long press an item, then drag it")
    }

    fun startDragging(index: Int) {
        draggingIndex = index
        hoveredIndex = -1
        dragX = 0f
        dragY = 0f
        message = "Dragging ${items[index].name}"
    }

    fun updateDragging(x: Float, y: Float) {
        dragX += x
        dragY += y

        hoveredIndex = findDropTarget(
            draggingIndex = draggingIndex,
            dragX = dragX,
            dragY = dragY,
            slotBounds = slotBounds
        )
    }

    fun finishDragging() {
        val sourceIndex = draggingIndex

        val targetIndex = findDropTarget(
            draggingIndex = sourceIndex,
            dragX = dragX,
            dragY = dragY,
            slotBounds = slotBounds
        )

        if (
            sourceIndex != -1 &&
            targetIndex != -1 &&
            sourceIndex != targetIndex
        ) {
            val temporaryItem = items[sourceIndex]

            items[sourceIndex] = items[targetIndex]
            items[targetIndex] = temporaryItem

            message = "Items switched!"
        } else {
            message = "Drop directly on another item"
        }

        draggingIndex = -1
        hoveredIndex = -1
        dragX = 0f
        dragY = 0f
    }

    fun cancelDragging() {
        draggingIndex = -1
        hoveredIndex = -1
        dragX = 0f
        dragY = 0f
        message = "Drag cancelled"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FB))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Drag & Drop Grid",
                color = Color(0xFF143C6D),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .width(320.dp)
                    .height(292.dp)
            ) {
                gridPositions.forEach { position ->
                    DraggableGridItem(
                        index = position.index,
                        item = items[position.index],
                        slotX = position.x,
                        slotY = position.y,
                        cardWidth = position.width,
                        cardHeight = position.height,
                        isSquare = position.isSquare,
                        isDragging = draggingIndex == position.index,
                        isHovered = hoveredIndex == position.index,
                        dragX = dragX,
                        dragY = dragY,
                        onBoundsChanged = { bounds ->
                            slotBounds[position.index] = bounds
                        },
                        onStart = {
                            startDragging(position.index)
                        },
                        onDrag = { x, y ->
                            updateDragging(x, y)
                        },
                        onDrop = {
                            finishDragging()
                        },
                        onCancel = {
                            cancelDragging()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DraggableGridItem(
    index: Int,
    item: DragItem,
    slotX: Dp,
    slotY: Dp,
    cardWidth: Dp,
    cardHeight: Dp,
    isSquare: Boolean,
    isDragging: Boolean,
    isHovered: Boolean,
    dragX: Float,
    dragY: Float,
    onBoundsChanged: (Rect) -> Unit,
    onStart: () -> Unit,
    onDrag: (Float, Float) -> Unit,
    onDrop: () -> Unit,
    onCancel: () -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current

    val animatedColor by animateColorAsState(
        targetValue = item.color,
        animationSpec = tween(durationMillis = 350),
        label = "itemColor"
    )

    val animatedScale by animateFloatAsState(
        targetValue = when {
            isDragging -> 1.08f
            isHovered -> 1.06f
            else -> 1f
        },
        animationSpec = tween(durationMillis = 150),
        label = "itemScale"
    )

    Box(
        modifier = Modifier
            .offset(
                x = slotX,
                y = slotY
            )
            .width(cardWidth)
            .height(cardHeight)
            .zIndex(if (isDragging) 10f else 0f)
            .onGloballyPositioned { coordinates ->
                // Every item now uses the same root coordinate system.
                onBoundsChanged(coordinates.boundsInRoot())
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .then(
                    if (isDragging) {
                        Modifier.offset {
                            IntOffset(
                                x = dragX.roundToInt(),
                                y = dragY.roundToInt()
                            )
                        }
                    } else {
                        Modifier
                    }
                )
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    shadowElevation = if (isDragging) 24f else 4f
                }
                .then(
                    if (isSquare) {
                        Modifier.size(88.dp)
                    } else {
                        Modifier
                            .width(cardWidth)
                            .height(cardHeight)
                    }
                )
                .clip(RoundedCornerShape(16.dp))
                .background(animatedColor)
                .border(
                    width = if (isHovered) 4.dp else 2.dp,
                    color = if (isHovered) {
                        Color(0xFFFFD600)
                    } else {
                        Color.White.copy(alpha = 0.5f)
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .pointerInput(index, item.id) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = {
                            hapticFeedback.performHapticFeedback(
                                HapticFeedbackType.LongPress
                            )
                            onStart()
                        },
                        onDragEnd = {
                            onDrop()
                        },
                        onDragCancel = {
                            onCancel()
                        }
                    ) { _, dragAmount ->
                        onDrag(
                            dragAmount.x,
                            dragAmount.y
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Crossfade(
                targetState = item,
                animationSpec = tween(durationMillis = 350),
                label = "switchAnimation"
            ) { displayedItem ->
                Text(
                    text = displayedItem.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun findDropTarget(
    draggingIndex: Int,
    dragX: Float,
    dragY: Float,
    slotBounds: Map<Int, Rect>
): Int {
    if (draggingIndex == -1) {
        return -1
    }

    val originalBounds = slotBounds[draggingIndex] ?: return -1

    val draggedCenter = Offset(
        x = originalBounds.center.x + dragX,
        y = originalBounds.center.y + dragY
    )

    return slotBounds.entries
        .firstOrNull { entry ->
            entry.key != draggingIndex &&
                    entry.value.contains(draggedCenter)
        }
        ?.key
        ?: -1
}