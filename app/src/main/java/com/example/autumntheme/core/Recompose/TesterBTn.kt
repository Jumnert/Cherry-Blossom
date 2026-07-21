package com.example.autumntheme.core.Recompose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// 1. The Parent (The Manager) owns the state
@Composable
fun AnimatedButton() {
    var isExpanded by remember { mutableStateOf(false) }

    // Instead of passing a hardcoded color, we let this function animate it
    // whenever the boolean changes.
    val backgroundColor by animateColorAsState(
        targetValue = if (isExpanded) Color.Blue else Color.Gray,
        label = "ColorAnimation" // Optional, helps with debugging
    )

    // Animate DP (pixels) for sizing
    val buttonWidth by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else 100.dp,
        label = "WidthAnimation"
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) { Box(
        modifier = Modifier
            .width(buttonWidth)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .clickable { isExpanded = !isExpanded } // Toggle state on click
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = if (isExpanded) "Shrink" else "Expand", color = Color.White)
    }}

}

@Preview
@Composable
private fun btn() {
    AnimatedButton()
}