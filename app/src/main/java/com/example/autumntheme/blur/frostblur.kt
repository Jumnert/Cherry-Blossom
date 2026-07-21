package com.example.autumntheme.blur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.R


@Composable
fun PremiumBakingGlassScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.img_premuim),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(28.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A1600).copy(alpha = 0.18f))
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 24.dp,
                bottom = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(10) { index ->
                PremiumFrostedCard(
                    title = "Premium Cake ${index + 1}",
                    subtitle = "Golden bakery item"
                )
            }
        }
    }
}
@Composable
fun PremiumFrostedCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(28.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.34f),
                        Color(0xFFFFD76A).copy(alpha = 0.18f),
                        Color.White.copy(alpha = 0.10f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.65f),
                        Color(0xFFFFE8A3).copy(alpha = 0.35f),
                        Color.White.copy(alpha = 0.18f)
                    )
                ),
                shape = shape
            )
            .padding(18.dp)
    ) {
        Column {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.78f),
                fontSize = 14.sp
            )
        }
    }
}
@Preview
@Composable
private fun Test() {
    PremiumBakingGlassScreen()
}