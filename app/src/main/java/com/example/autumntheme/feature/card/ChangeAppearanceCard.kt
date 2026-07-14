package com.example.autumntheme.feature.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autumntheme.R
import com.example.autumntheme.ui.theme.AmberGold
import com.example.autumntheme.ui.theme.BorderTan
import com.example.autumntheme.ui.theme.CherryBlossomPink
import com.example.autumntheme.ui.theme.CherryDeepPink
import com.example.autumntheme.ui.theme.CherryVibrantPink
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.PumpkinOrange
import com.example.autumntheme.ui.theme.WarmCream

/**
 * Data class to define all theme-able properties of the card.
 */
data class CardTheme(
    val name: String,
    val backgroundRes: Int,
    val buttonColor: Color,
    val buttonTextColor: Color,
    val iconBorderColor: Color = Color.Transparent,
    val iconCornerRadius: Dp = 6.dp,
    val themeImages: List<Int>,
    val sheetContainerColor: Color,
    val sheetContentColor: Color,
)

// 1. Current Dark Blue Theme
val DarkBlueTheme = CardTheme(
    name = "Dark Blue",
    backgroundRes = R.drawable.theme_bgjpg,
    buttonColor = Color(0xFF132D48),
    buttonTextColor = Color.White,
    themeImages = listOf(
        R.drawable.img_theme1,
        R.drawable.img_theme2,
        R.drawable.img_theme3,
        R.drawable.img_theme4,
    ),
    sheetContainerColor = Color(0xFF0F172A),
    sheetContentColor = Color(0xFF1E293B)
)

// 2. Autumn Theme
val AutumnTheme = CardTheme(
    name = "Autumn",
    backgroundRes = R.drawable.banner4,
    buttonColor = PumpkinOrange,
    buttonTextColor = Color.White,
    iconBorderColor = AmberGold,
    iconCornerRadius = 12.dp,
    themeImages = listOf(
        R.drawable.banner1,
        R.drawable.banner4,
        R.drawable.banner1,
        R.drawable.banner4,
    ),
    sheetContainerColor = DeepBrown,
    sheetContentColor = BorderTan
)

// 3. New Forest Theme
val ForestTheme = CardTheme(
    name = "Forest",
    backgroundRes = R.drawable.banner1,
    buttonColor = Color(0xFF1B5E20),
    buttonTextColor = Color.White,
    iconBorderColor = Color(0xFFC8E6C9),
    iconCornerRadius = 24.dp, // Extra rounded
    themeImages = listOf(
        R.drawable.img_theme4,
        R.drawable.img_theme3,
        R.drawable.img_theme2,
        R.drawable.img_theme1,
    ),
    sheetContainerColor = Color(0xFF002111),
    sheetContentColor = Color(0xFF00391C)
)

// 4. Cherry Blossom Theme
val CherryBlossomTheme = CardTheme(
    name = "Cherry Blossom",
    backgroundRes = R.drawable.banner2,
    buttonColor = CherryVibrantPink,
    buttonTextColor = Color.White,
    iconBorderColor = CherryBlossomPink,
    iconCornerRadius = 18.dp,
    themeImages = listOf(
        R.drawable.img_theme5,
        R.drawable.img_theme1,
        R.drawable.img_theme2,
        R.drawable.img_theme3,
    ),
    sheetContainerColor = CherryDeepPink,
    sheetContentColor = CherryVibrantPink
)

val AllThemes = listOf(DarkBlueTheme, AutumnTheme, ForestTheme, CherryBlossomTheme)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenshotThemeCard(
    currentTheme: CardTheme,
    availableThemes: List<CardTheme> = AllThemes,
    onThemeSelected: (CardTheme) -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(18.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(18.dp))
        ) {
            Image(
                painter = painterResource(id = currentTheme.backgroundRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Row(
                modifier = Modifier.matchParentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 12.dp, end = 2.dp)
                        .clipToBounds()
                ) {
                    currentTheme.themeImages.forEachIndexed { index, imageRes ->
                        val yOffset = when (index) {
                            0 -> 50.dp
                            1 -> 65.dp
                            2 -> 35.dp
                            3 -> 65.dp
                            else -> 0.dp
                        }

                        Box(
                            modifier = Modifier
                                .width(52.dp)
                                .fillMaxHeight()
                        ) {
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = "Icon ${index + 1}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .offset(y = yOffset)
                                    .clip(RoundedCornerShape(currentTheme.iconCornerRadius))
                                    .border(
                                        width = 1.dp,
                                        color = currentTheme.iconBorderColor,
                                        shape = RoundedCornerShape(currentTheme.iconCornerRadius)
                                    )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Customize Button
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 20.dp)
                        .width(110.dp)
                        .height(36.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(18.dp))
                        .clip(RoundedCornerShape(18.dp))
                        .background(color = PumpkinOrange)
                        .clickable { showSheet = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Customize",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarmCream
                    )
                }

                if (showSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showSheet = false },
                        sheetState = sheetState,
                        containerColor = currentTheme.sheetContainerColor,
                        dragHandle = { BottomSheetDefaults.DragHandle() }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 4.dp)
                                .navigationBarsPadding()
                        ) {
                            Text(
                                text = "Select Theme",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(currentTheme.sheetContentColor, RoundedCornerShape(16.dp))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(availableThemes) { themeOption ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.clickable { 
                                            onThemeSelected(themeOption)
                                        }
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(100.dp, 120.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .border(
                                                    width = if (currentTheme.name == themeOption.name) 3.dp else 1.dp,
                                                    color = if (currentTheme.name == themeOption.name) Color.White else Color.Transparent,
                                                    shape = RoundedCornerShape(12.dp)
                                                )
                                        ) {
                                            Image(
                                                painter = painterResource(themeOption.backgroundRes),
                                                contentDescription = themeOption.name,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                        Text(
                                            text = themeOption.name,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeSwitchingScreen() {
    var currentTheme by remember { mutableStateOf(DarkBlueTheme) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0B))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ScreenshotThemeCard(
            currentTheme = currentTheme,
            onThemeSelected = { newTheme -> currentTheme = newTheme }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Tap 'Customize' to change themes inside the Sheet",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomThemeCardPreview() {
    ThemeSwitchingScreen()
}
