package com.example.autumntheme.feature.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.compose.AsyncImage
import com.example.autumntheme.R
import com.example.autumntheme.feature.home.CardColor
import com.example.autumntheme.ui.theme.AmberGold
import com.example.autumntheme.ui.theme.BorderTan
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.PumpkinOrange
import com.example.autumntheme.ui.theme.WarmCream

val DarkBlue = Color(0xFF18314D)

@Composable
fun SpecialOfferCard(
    modifier: Modifier = Modifier,
    imgPlaceholder: Int,
    title: String,
    theme: CardTheme = AutumnTheme
) {
    val context = LocalContext.current
    val imageRequest = remember(imgPlaceholder) {
        ImageRequest.Builder(context)
            .data(imgPlaceholder)
            .crossfade(true)
            .allowHardware(true)
            .build()
    }
    val imagePainter = rememberAsyncImagePainter(model = imageRequest)

    ElevatedCard(
        modifier = modifier
            .width(150.dp)
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = theme.cardBackgroundColor.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = imagePainter,
                contentDescription = title,
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .weight(0.7f),
                contentScale = ContentScale.Crop
            )
                Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = title,
                    color = theme.secondaryTextColor,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun offercardcarousel(modifier: Modifier = Modifier, theme: CardTheme = AutumnTheme) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SpecialOfferCard(imgPlaceholder = R.drawable.img_def_offer1, title = "get 50% Off at Clinic", modifier = Modifier.padding(start = 16.dp), theme = theme)
        }
        item {
            SpecialOfferCard(imgPlaceholder = R.drawable.img_def_offer2, title = "Pay with ACLEDA save 50% on Store", theme = theme)
        }
        item {
            SpecialOfferCard(imgPlaceholder = R.drawable.img_def_offer3, title = "Pay for fuel cheaper with ACLEDA ", theme = theme)
        }
        item {
            SpecialOfferCard(imgPlaceholder = R.drawable.img_def_offer4, title = "Discount up to 10% with ACLEDA Cards", theme = theme)
        }
        item {
            SpecialOfferCard(imgPlaceholder = R.drawable.img_def_offer3, title = "Pay for fuel cheaper with ACLEDA ", theme = theme)
        }
        item {
            SpecialOfferCard(imgPlaceholder = R.drawable.img_def_offer4, title = "Discount up to 10% with ACLEDA Cards", modifier = Modifier.padding(end = 16.dp), theme = theme)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpecialOfferCardPreview() {
    SpecialOfferCard(
        imgPlaceholder = R.drawable.img_def_offer4,
        title = "A Special Promotion Offer"
    )
}