package com.example.autumntheme.feature.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.autumntheme.R

@Composable
fun PropertyCard(
    modifier: Modifier = Modifier,
    imagePlaceholder: Int,
    title: String,
    profileImage: Int
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // 1. BASE LAYER: perfectly sharp background image
            AsyncImage(
                model = imagePlaceholder,
                contentDescription = "Property Preview",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 2. GRADIENT OVERLAY (Reduced complexity for performance)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.7f),
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), 
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = profileImage,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = title,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold,
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

data class CarouselItem(
    val title: String,
    val imagePlaceholder: Int,
    val profileImage: Int
)

@Composable
fun PropertyCarousel(
    modifier: Modifier = Modifier,
    items: List<CarouselItem>
) {
    if (items.isEmpty()) return

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val horizontalPadding = 0.dp
    val cardSpacing = 0.dp

    // Dynamic scale math: Allocates clean proportions so 2 full items
    // and exactly 40% of the 3rd item fit across any device screen width.
    val calculatedCardWidth = (screenWidth - (horizontalPadding * 2) - (cardSpacing * 2)) / 2.4f

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(cardSpacing)
    ) {
        items(items) { item ->
            PropertyCard(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(250.dp)
                    .height(180.dp), // Feeds width dynamically into the Modifier chain above
                title = item.title,
                imagePlaceholder = item.imagePlaceholder,
                profileImage = item.profileImage
            )
        }
    }
}

@Preview
@Composable
private fun previe12() {
    PropertyCard(
        modifier = Modifier.width(300.dp).height(180.dp),
        title = "Student in rural area studying at young age was captured by the moeys team",
        imagePlaceholder = R.drawable.img_studentstudy,
        profileImage = R.drawable.img_moeys
    )
}