package com.example.autumntheme

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import com.example.autumntheme.blur.BlurredContentCard
import com.example.autumntheme.core.Recompose.AnimatedButton
import com.example.autumntheme.dragdrop.DragDropGridScreen
import com.example.autumntheme.feature.card.GlassCard
import com.example.autumntheme.feature.receipt.ReceiptScreen
import com.example.autumntheme.navigation.AppNavGraph
import com.example.autumntheme.ui.theme.Training15DaysTheme
import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window?.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )

        enableEdgeToEdge()
        setContent {
            Training15DaysTheme {
//                AnimatedButton()
//                BlurredContentCard()
                AppNavGraph()
//                AppNavGra
//                DragDropGridScreen()
//                ReceiptScreen()
                }
            }
        }
    }


@Preview
@Composable
private fun HomeScreenPreview() {
    AppNavGraph()
}