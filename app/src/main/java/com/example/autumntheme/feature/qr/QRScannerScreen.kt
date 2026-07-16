package com.example.autumntheme.feature.qr

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Rect
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.LifecycleCameraController
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.autumntheme.R
import com.example.autumntheme.feature.card.CardTheme
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalGetImage::class)
@Composable
fun QRScannerScreen(
    theme: CardTheme,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

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

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                androidx.camera.view.CameraController.IMAGE_ANALYSIS
            )
        }
    }

    val options = remember(cameraController) {
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }

    val scanner = remember(options) { BarcodeScanning.getClient(options) }
    var isScanned by remember { mutableStateOf(false) }

    // Bounding box state for the target scanning UI
    var qrBoundingBox by remember { mutableStateOf<Rect?>(null) }
    var rawImageDimensions by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var imageRotation by remember { mutableStateOf(0) }

    var minZoomRatio by remember { mutableStateOf(1f) }

    LaunchedEffect(cameraController, scanner) {
        cameraController.bindToLifecycle(lifecycleOwner)
        
        coroutineScope.launch {
            while (cameraController.zoomState.value == null) {
                delay(50)
            }
            val mz = cameraController.zoomState.value?.minZoomRatio ?: 1f
            if (mz < 1f) {
                minZoomRatio = mz
                cameraController.setZoomRatio(mz)
            }
        }

        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(context)
        ) { imageProxy ->
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        val barcode = barcodes.firstOrNull()
                        if (barcode != null) {
                            // Track raw bounding box and rotation parameters
                            qrBoundingBox = barcode.boundingBox
                            rawImageDimensions = Pair(imageProxy.width, imageProxy.height)
                            imageRotation = imageProxy.imageInfo.rotationDegrees

                            val qrCode = barcode.rawValue
                            if (qrCode != null && !isScanned) {
                                isScanned = true
                                // Hold screen momentarily so user sees visual overlay lock-on
                                coroutineScope.launch {
                                    delay(1500)
                                    onDismiss()
                                }
                            }
                        } else {
                            qrBoundingBox = null
                        }
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            } else {
                imageProxy.close()
            }
        }
    }

    val defaultScale = if (minZoomRatio < 1f) 1f / minZoomRatio else 1.0f
    var targetScale by remember { mutableStateOf(1f) }
    var targetTransX by remember { mutableStateOf(0f) }
    var targetTransY by remember { mutableStateOf(0f) }

    LaunchedEffect(defaultScale) {
        targetScale = defaultScale
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        val screenWidth = constraints.maxWidth.toFloat()
        val screenHeight = constraints.maxHeight.toFloat()

        LaunchedEffect(qrBoundingBox, rawImageDimensions, imageRotation, defaultScale) {
            if (qrBoundingBox != null && rawImageDimensions != null) {
                val imgW = rawImageDimensions!!.first.toFloat()
                val imgH = rawImageDimensions!!.second.toFloat()

                val scaleX: Float
                val scaleY: Float
                if (imageRotation == 90 || imageRotation == 270) {
                    scaleX = screenWidth / imgH
                    scaleY = screenHeight / imgW
                } else {
                    scaleX = screenWidth / imgW
                    scaleY = screenHeight / imgH
                }

                val qrCenterX: Float
                val qrCenterY: Float
                when (imageRotation) {
                    90 -> {
                        qrCenterX = qrBoundingBox!!.centerY().toFloat() * scaleX
                        qrCenterY = (imgW - qrBoundingBox!!.centerX().toFloat()) * scaleY
                    }
                    270 -> {
                        qrCenterX = (imgH - qrBoundingBox!!.centerY().toFloat()) * scaleX
                        qrCenterY = qrBoundingBox!!.centerX().toFloat() * scaleY
                    }
                    180 -> {
                        qrCenterX = (imgW - qrBoundingBox!!.centerX().toFloat()) * scaleX
                        qrCenterY = (imgH - qrBoundingBox!!.centerY().toFloat()) * scaleY
                    }
                    else -> {
                        qrCenterX = qrBoundingBox!!.centerX().toFloat() * scaleX
                        qrCenterY = qrBoundingBox!!.centerY().toFloat() * scaleY
                    }
                }

                val qrWidth = qrBoundingBox!!.width().toFloat() * scaleX
                val qrHeight = qrBoundingBox!!.height().toFloat() * scaleY
                val maxQrDim = maxOf(qrWidth, qrHeight)

                // We want the QR code to take up roughly 40% of the smallest screen dimension
                val minScreenDim = minOf(screenWidth, screenHeight)
                val desiredScale = if (maxQrDim > 0) {
                    (minScreenDim * 0.4f) / maxQrDim
                } else {
                    defaultScale * 1.5f
                }
                
                targetScale = desiredScale.coerceIn(defaultScale * 1.2f, defaultScale * 4f)
                targetTransX = (screenWidth / 2f - qrCenterX) * targetScale
                targetTransY = (screenHeight / 2f - qrCenterY) * targetScale
            } else {
                targetScale = defaultScale
                targetTransX = 0f
                targetTransY = 0f
            }
        }

        val animScale by animateFloatAsState(targetScale, animationSpec = tween(500, easing = FastOutSlowInEasing), label = "animScale")
        val animTransX by animateFloatAsState(targetTransX, animationSpec = tween(500, easing = FastOutSlowInEasing), label = "animTransX")
        val animTransY by animateFloatAsState(targetTransY, animationSpec = tween(500, easing = FastOutSlowInEasing), label = "animTransY")

        if (hasCameraPermission) {
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        controller = cameraController
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = animScale
                        scaleY = animScale
                        translationX = animTransX
                        translationY = animTransY
                    }
            )
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

        ScannerOverlay(
            theme = theme,
            qrBox = qrBoundingBox,
            imageSize = rawImageDimensions,
            rotation = imageRotation,
            animScale = animScale,
            animTransX = animTransX,
            animTransY = animTransY,
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )

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

        BottomScannerUI(cameraController = cameraController, theme = theme)
    }
}

@Composable
fun ScannerOverlay(
    theme: CardTheme,
    qrBox: Rect?,
    imageSize: Pair<Int, Int>?,
    rotation: Int,
    animScale: Float,
    animTransX: Float,
    animTransY: Float,
    screenWidth: Float,
    screenHeight: Float
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scannerAnimation")
    val density = LocalDensity.current

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

    // Determine target box positions based on detected QR coordinates or fallback centered box
    var targetLeft = 0f
    var targetTop = 0f
    var targetRight = 0f
    var targetBottom = 0f
    var hasTarget = false

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = constraints.maxWidth.toFloat()
        val screenHeight = constraints.maxHeight.toFloat()

        if (qrBox != null && imageSize != null) {
            val imgW = imageSize.first.toFloat()
            val imgH = imageSize.second.toFloat()

            val scaleX: Float
            val scaleY: Float
            if (rotation == 90 || rotation == 270) {
                scaleX = screenWidth / imgH
                scaleY = screenHeight / imgW
            } else {
                scaleX = screenWidth / imgW
                scaleY = screenHeight / imgH
            }

            var rawLeft = 0f
            var rawTop = 0f
            var rawRight = 0f
            var rawBottom = 0f

            when (rotation) {
                90 -> {
                    rawLeft = qrBox.top * scaleX
                    rawTop = (imgW - qrBox.right) * scaleY
                    rawRight = qrBox.bottom * scaleX
                    rawBottom = (imgW - qrBox.left) * scaleY
                }
                270 -> {
                    rawLeft = (imgH - qrBox.bottom) * scaleX
                    rawTop = qrBox.left * scaleY
                    rawRight = (imgH - qrBox.top) * scaleX
                    rawBottom = qrBox.right * scaleY
                }
                180 -> {
                    rawLeft = (imgW - qrBox.right) * scaleX
                    rawTop = (imgH - qrBox.bottom) * scaleY
                    rawRight = (imgW - qrBox.left) * scaleX
                    rawBottom = (imgH - qrBox.top) * scaleY
                }
                else -> {
                    rawLeft = qrBox.left * scaleX
                    rawTop = qrBox.top * scaleY
                    rawRight = qrBox.right * scaleX
                    rawBottom = qrBox.bottom * scaleY
                }
            }

            // Map raw coordinates to visually transformed coordinates
            targetLeft = (rawLeft - screenWidth / 2f) * animScale + screenWidth / 2f + animTransX
            targetTop = (rawTop - screenHeight / 2f) * animScale + screenHeight / 2f + animTransY
            targetRight = (rawRight - screenWidth / 2f) * animScale + screenWidth / 2f + animTransX
            targetBottom = (rawBottom - screenHeight / 2f) * animScale + screenHeight / 2f + animTransY

            val padding = with(density) { 15.dp.toPx() } * animScale
            targetLeft -= padding
            targetTop -= padding
            targetRight += padding
            targetBottom += padding
            hasTarget = true
        }

        if (!hasTarget) {
            val baseBoxSize = with(density) { 260.dp.toPx() }
            val boxSize = baseBoxSize * cornerPulse
            targetLeft = (screenWidth - boxSize) / 2
            targetTop = (screenHeight - boxSize) / 3f
            targetRight = targetLeft + boxSize
            targetBottom = targetTop + boxSize
        }

        // Smooth animations for transitions
        val animLeft by animateFloatAsState(targetValue = targetLeft, animationSpec = spring(stiffness = Spring.StiffnessLow))
        val animTop by animateFloatAsState(targetValue = targetTop, animationSpec = spring(stiffness = Spring.StiffnessLow))
        val animRight by animateFloatAsState(targetValue = targetRight, animationSpec = spring(stiffness = Spring.StiffnessLow))
        val animBottom by animateFloatAsState(targetValue = targetBottom, animationSpec = spring(stiffness = Spring.StiffnessLow))

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        ) {
            val cornerRadius = 24.dp.toPx()
            val boxWidth = animRight - animLeft
            val boxHeight = animBottom - animTop

            // Draw translucent overlay mask
            drawRect(
                color = Color.Black.copy(alpha = 0.6f),
                size = size
            )

            // Clear visual frame target area
            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(animLeft, animTop),
                size = Size(boxWidth, boxHeight),
                cornerRadius = CornerRadius(cornerRadius),
                blendMode = BlendMode.Clear
            )

            // Draw bracketed corners
            val lineLength = 32.dp.toPx()
            val strokeWidth = 5.dp.toPx()

            // Top-Left corner
            val topLeftPath = Path().apply {
                moveTo(animLeft, animTop + lineLength)
                lineTo(animLeft, animTop + cornerRadius)
                quadraticTo(animLeft, animTop, animLeft + cornerRadius, animTop)
                lineTo(animLeft + lineLength, animTop)
            }
            drawPath(topLeftPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

            // Top-Right corner
            val topRightPath = Path().apply {
                moveTo(animRight - lineLength, animTop)
                lineTo(animRight - cornerRadius, animTop)
                quadraticTo(animRight, animTop, animRight, animTop + cornerRadius)
                lineTo(animRight, animTop + lineLength)
            }
            drawPath(topRightPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

            // Bottom-Left corner
            val bottomLeftPath = Path().apply {
                moveTo(animLeft, animBottom - lineLength)
                lineTo(animLeft, animBottom - cornerRadius)
                quadraticTo(animLeft, animBottom, animLeft + cornerRadius, animBottom)
                lineTo(animLeft + lineLength, animBottom)
            }
            drawPath(bottomLeftPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

            // Bottom-Right corner
            val bottomRightPath = Path().apply {
                moveTo(animRight, animBottom - lineLength)
                lineTo(animRight, animBottom - cornerRadius)
                quadraticTo(animRight, animBottom, animRight - cornerRadius, animBottom)
                lineTo(animRight - lineLength, animBottom)
            }
            drawPath(bottomRightPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

            // Draw the moving laser line inside the dynamic box
            val laserY = animTop + (boxHeight * laserPositionProgress)
            val laserThickness = 4.dp.toPx()

            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Transparent, laserColor, Color.Transparent),
                    startX = animLeft,
                    endX = animRight
                ),
                topLeft = Offset(animLeft, laserY - laserThickness / 2),
                size = Size(boxWidth, laserThickness)
            )
        }
    }
}

@Composable
fun BottomScannerUI(cameraController: LifecycleCameraController, theme: CardTheme) {
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
                        cameraController.enableTorch(isOn)
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