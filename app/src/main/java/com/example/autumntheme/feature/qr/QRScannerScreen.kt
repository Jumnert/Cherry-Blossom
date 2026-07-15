package com.example.autumntheme.feature.qr

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.autumntheme.R
import com.example.autumntheme.feature.card.CardTheme
import kotlin.math.absoluteValue

@Composable
fun QRScannerScreen(
    theme: CardTheme,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var camera by remember { mutableStateOf<Camera?>(null) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        if (hasCameraPermission) {
            CameraPreview(onCameraReady = { camera = it })
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Camera permission required to scan QR codes",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        ScannerOverlay(theme = theme)

        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .size(40.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        BottomScannerUI(camera = camera, theme = theme)
    }
}

@Composable
fun CameraPreview(onCameraReady: (Camera) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(factory = { ctx ->
        val previewView = PreviewView(ctx)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            cameraProvider.unbindAll()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            val imageAnalysis = ImageAnalysis.Builder().build()

            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageAnalysis
            )

            onCameraReady(camera)
        }, ContextCompat.getMainExecutor(ctx))

        previewView
    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun ScannerOverlay(theme: CardTheme) {
    val infiniteTransition = rememberInfiniteTransition(label = "scannerAnimation")

    val laserPositionProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laserProgress"
    )

    val cornerPulse by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cornerPulse"
    )

    val laserColor = theme.buttonColor
    val cornerColor = if (theme.iconBorderColor != Color.Transparent) theme.iconBorderColor else theme.buttonColor

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    ) {
        val width = size.width
        val height = size.height

        val baseBoxSize = 260.dp.toPx()
        val boxSize = baseBoxSize * cornerPulse
        val cornerRadius = 32.dp.toPx()

        val left = (width - boxSize) / 2
        val top = (height - boxSize) / 3f
        val right = left + boxSize
        val bottom = top + boxSize

        drawRect(
            color = Color.Black.copy(alpha = 0.6f),
            size = size
        )

        drawRoundRect(
            color = Color.Transparent,
            topLeft = Offset(left, top),
            size = Size(boxSize, boxSize),
            cornerRadius = CornerRadius(cornerRadius),
            blendMode = BlendMode.Clear
        )

        val lineLength = 40.dp.toPx()
        val strokeWidth = 6.dp.toPx()

        val topLeftPath = Path().apply {
            moveTo(left, top + lineLength)
            lineTo(left, top + cornerRadius)
            quadraticTo(left, top, left + cornerRadius, top)
            lineTo(left + lineLength, top)
        }
        drawPath(topLeftPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

        val topRightPath = Path().apply {
            moveTo(right - lineLength, top)
            lineTo(right - cornerRadius, top)
            quadraticTo(right, top, right, top + cornerRadius)
            lineTo(right, top + lineLength)
        }
        drawPath(topRightPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

        val bottomLeftPath = Path().apply {
            moveTo(left, bottom - lineLength)
            lineTo(left, bottom - cornerRadius)
            quadraticTo(left, bottom, left + cornerRadius, bottom)
            lineTo(left + lineLength, bottom)
        }
        drawPath(bottomLeftPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

        val bottomRightPath = Path().apply {
            moveTo(right, bottom - lineLength)
            lineTo(right, bottom - cornerRadius)
            quadraticTo(right, bottom, right - cornerRadius, bottom)
            lineTo(right - lineLength, bottom)
        }
        drawPath(bottomRightPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

        val laserY = top + (boxSize * laserPositionProgress)
        val laserThickness = 4.dp.toPx()

        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Transparent, laserColor, Color.Transparent),
                startX = left,
                endX = right
            ),
            topLeft = Offset(left, laserY - laserThickness / 2),
            size = Size(boxSize, laserThickness)
        )
    }
}

@Composable
fun BottomScannerUI(camera: Camera?, theme: CardTheme) {
    var isOn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "We Support",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(14.dp))

        val supportIcons = listOf(
            R.drawable.khqr,
            R.drawable.visa,
            R.drawable.mastercard,
            R.drawable.jpor,
            R.drawable.acleda,
            R.drawable.duitnow
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(supportIcons) { resId ->
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = "Support Icon",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(24.dp)
                        .width(40.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = {
                        isOn = !isOn
                        camera?.cameraControl?.enableTorch(isOn)
                    },
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            if (isOn) theme.buttonColor.copy(alpha = 0.4f) else Color.White.copy(alpha = 0.1f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (isOn) Icons.Filled.FlashlightOn else Icons.Filled.FlashlightOff,
                        contentDescription = "Flashlight",
                        tint = if (isOn) theme.buttonColor else Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isOn) "On" else "Flashlight",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.White.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Image,
                        contentDescription = "Upload QR",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Upload QR", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}