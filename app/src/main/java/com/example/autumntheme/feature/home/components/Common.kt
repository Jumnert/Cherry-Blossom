package com.example.autumntheme.feature.home.components

import com.example.autumntheme.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme
import com.example.autumntheme.ui.theme.CardBrown
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.TextTanGold
import com.example.autumntheme.ui.theme.WarmCream

/**
 * Optimised dashboard icon component.
 *
 * Why Coil instead of painterResource?
 * Some user-provided theme icons (like ic_frost_*) are extremely high resolution
 * (around 1.5MB to 2MB each, 2000x2000 pixels). Loading them synchronously with
 * painterResource causes full-resolution main-thread decoding, resulting in severe
 * UI lag and massive RAM usage.
 *
 * Using Coil's rememberAsyncImagePainter allows us to decode the image on a background
 * thread and force downscaling (e.g. to 128x128 pixels), which runs smoothly at 60fps.
 */
@Composable
fun DashboardIcon(
    iconRes: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    // Explicitly target 128x128 pixels for the decoded bitmap to keep memory footprint tiny
    val request = remember(iconRes) {
        ImageRequest.Builder(context)
            .data(iconRes)
            .size(128, 128)
            .crossfade(true)
            .allowHardware(true)
            .build()
    }
    
    Image(
        painter = rememberAsyncImagePainter(model = request),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun RomdoulCardTexture(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.img_romdoul_texture),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        alpha = 0.30f
    )
}

@Composable
fun HeaderText(text: String, modifier: Modifier = Modifier, theme: CardTheme = AutumnTheme) {
    Text(
        color = theme.primaryTextColor,
        modifier = modifier.padding(5.dp),
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )
}
