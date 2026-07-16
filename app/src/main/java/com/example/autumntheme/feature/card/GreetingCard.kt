package com.example.autumntheme.feature.card
import com.example.autumntheme.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.autumntheme.ui.theme.DeepBrown

@Composable
fun GreetingCard(modifier: Modifier = Modifier, theme: CardTheme = AutumnTheme) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(50.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_def_profile),
            contentDescription = "User profile",
            modifier = Modifier.clip(CircleShape)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                fontSize = 18.sp,
                color = theme.primaryTextColor,
                text = "Hi \uD83D\uDC4B, Theachumnith",
                fontWeight = FontWeight.Medium,
            )
            Text(
                fontSize = 16.sp,
                text = "Profile >",
                color = theme.primaryTextColor,
            )
        }
    }
}

@Preview
@Composable
private fun GreetingCardPreview() {
    GreetingCard()
}