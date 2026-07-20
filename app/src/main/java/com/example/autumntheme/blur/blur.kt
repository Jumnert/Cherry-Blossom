package com.example.autumntheme.blur

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.autumntheme.R

@Composable
fun BlurredContentCard() {
    Card(
        modifier = Modifier
            .size(300.dp, 200.dp)
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.duitnow),
                contentDescription = "Blurred Background Image",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radius = 16.dp)
            )
            Text(
                text = "Blur tester"
            )
        }
    }
}
