package com.example.autumntheme.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autumntheme.feature.card.AutumnTheme
import com.example.autumntheme.feature.card.CardTheme
import com.example.autumntheme.feature.home.HomeScreen
import com.example.autumntheme.feature.qr.QRScannerScreen
import com.example.autumntheme.feature.receipt.ReceiptScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object ScannerRoute

@Serializable
object ReceiptRoute

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    var currentTheme by remember { mutableStateOf(AutumnTheme) }

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            HomeScreen(
                currentTheme = currentTheme,
                onThemeSelected = { newTheme -> currentTheme = newTheme },
                onNavigateToScanner = {
                    navController.navigate(ScannerRoute)
                },
                onNavigateToReceipt = {
                    navController.navigate(ReceiptRoute)
                }
            )
        }
        composable<ScannerRoute> {
            QRScannerScreen(
                theme = currentTheme,
                onDismiss = {
                    navController.popBackStack()
                }
            )
        }
        composable<ReceiptRoute> {
            Box(modifier = Modifier.fillMaxSize()) {
                ReceiptScreen()
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(16.dp)
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
