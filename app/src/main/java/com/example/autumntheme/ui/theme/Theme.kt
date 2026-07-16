package com.example.autumntheme.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

// Complete Material 3 Light Color Scheme for Denim Theme
val CustomAppColorScheme = lightColorScheme(
    background = SnowBackground,
    surface = Color.White,                   // Clean crisp white for cards
    surfaceVariant = SoftBlueGray,           // Perfect for secondary containers or borders
    primary = DenimBlue,                     // Main active color / buttons
    onPrimary = Color.White,                 // Text on top of primary buttons
    onBackground = DeepNavy,                 // Headings and body text
    onSurface = DeepNavy,
    error = CoralAlert,                      // Your high-utility destructive color
    onError = Color.White
)

// Complete Material 3 Light Color Scheme for Matcha Theme
val MatchaColorScheme = lightColorScheme(
    background = MatchaBackground,
    surface = Color.White,                   // Clean crisp white for cards
    surfaceVariant = MatchaSage,             // Perfect for secondary containers or borders
    primary = MatchaPrimary,                 // Main active color / buttons
    onPrimary = Color.White,                 // Text on top of primary buttons
    onBackground = MatchaDarkForest,         // Headings and body text
    onSurface = MatchaDarkForest,
    error = CoralAlert,                      // Your high-utility destructive color
    onError = Color.White
)

@Composable
fun Training15DaysTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}