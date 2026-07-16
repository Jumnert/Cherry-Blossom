package com.example.autumntheme.feature.home.components

import com.example.autumntheme.R

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.autumntheme.feature.home.CardColor

data class ContactItem(val name: String, val avatarResId: Int)

@Composable
fun ContactCarousel(
    modifier: Modifier = Modifier,
    onContactSelect: (ContactItem) -> Unit = {}
) {
    val contactsList = listOf(
        ContactItem("Sok Dara", R.drawable.img_def_designer)
    )
    val context = LocalContext.current
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(contactsList) { contact ->
            val avatarPainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(contact.avatarResId)
                    .crossfade(true)
                    .allowHardware(true)
                    .build()
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(100.dp)
                    .height(120.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onContactSelect(contact) }
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = avatarPainter,
                        contentDescription = contact.name,
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = contact.name,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

