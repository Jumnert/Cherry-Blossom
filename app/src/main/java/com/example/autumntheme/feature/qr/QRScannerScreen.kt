package com.example.autumntheme.feature.qr

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Rect
import android.hardware.camera2.CameraCharacteristics
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import kotlin.OptIn
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.autumntheme.feature.card.CardTheme
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.Image
import com.example.autumntheme.R


@OptIn(ExperimentalGetImage::class, ExperimentalMaterial3Api::class)
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
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
        }
    }

    val options = remember {
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }
    val scanner = remember(options) { BarcodeScanning.getClient(options) }

    var qrContent by remember { mutableStateOf<String?>(null) }
    var isScanned by remember { mutableStateOf(false) }

    var qrBoundingBox by remember { mutableStateOf<Rect?>(null) }
    var rawImageDimensions by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var imageRotation by remember { mutableStateOf(0) }
    var lastQrDetectedTime by remember { mutableLongStateOf(0L) }

    var minZoomRatio by remember { mutableStateOf(1f) }
    var targetCameraZoom by remember { mutableStateOf(1f) }

    // Check if the camera has an ultra-wide lens (focal length < 24mm equivalent)
    val hasUltraWide = remember(cameraController.zoomState.value) {
        val minZoom = cameraController.zoomState.value?.minZoomRatio ?: 1f
        if (minZoom < 1f) {
            minZoomRatio = minZoom
            true
        } else {
            try {
                val cameraInfo = cameraController.cameraInfo
                if (cameraInfo != null) {
                    val camera2Info = Camera2CameraInfo.from(cameraInfo)
                    val focalLengths = camera2Info.getCameraCharacteristic(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS)
                    val sensorSize = camera2Info.getCameraCharacteristic(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE)
                    if (focalLengths != null && sensorSize != null) {
                        focalLengths.any { f -> (f * 36.0f / sensorSize.width) < 24f }
                    } else false
                } else false
            } catch (e: Exception) {
                false
            }
        }
    }

    // Coroutine loop to check the 1.5s timeout and reset zoom
    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            val now = System.currentTimeMillis()
            if (qrBoundingBox != null && now - lastQrDetectedTime > 1500L) {
                targetCameraZoom = 1f
                qrBoundingBox = null
            }
        }
    }

    // Zoom Animator: Animates the camera's zoomRatio smoothly using a lerp over 300ms
    LaunchedEffect(targetCameraZoom) {
        val currentZoom = cameraController.zoomState.value?.zoomRatio ?: 1f
        if (currentZoom != targetCameraZoom) {
            val duration = 300f
            val steps = 15
            val stepTime = (duration / steps).toLong()
            for (i in 1..steps) {
                val fraction = i.toFloat() / steps
                val lerped = currentZoom + (targetCameraZoom - currentZoom) * fraction
                cameraController.setZoomRatio(lerped)
                delay(stepTime)
            }
        }
    }

    // Bind CameraX Lifecycle and Setup Image Analysis
    LaunchedEffect(cameraController, scanner) {
        cameraController.bindToLifecycle(lifecycleOwner)

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
                            val box = barcode.boundingBox
                            if (box != null) {
                                qrBoundingBox = box
                                rawImageDimensions = Pair(imageProxy.width, imageProxy.height)
                                imageRotation = imageProxy.imageInfo.rotationDegrees
                                lastQrDetectedTime = System.currentTimeMillis()

                                val imgW = imageProxy.width.toFloat()
                                val imgH = imageProxy.height.toFloat()
                                val frameArea = imgW * imgH
                                val qrArea = box.width() * box.height()
                                val qrRatio = qrArea.toFloat() / frameArea

                                // Only trigger zoom if QR occupies less than 40% of the frame
                                if (qrRatio < 0.40f) {
                                    if (hasUltraWide) {
                                        // Switch to ultra-wide lens silently
                                        targetCameraZoom = minZoomRatio
                                    } else {
                                        // Apply 1.3x digital zoom (capped to 2.5x max)
                                        targetCameraZoom = 1.3f
                                    }
                                }
                            }

                            val qrCode = barcode.rawValue
                            if (qrCode != null && !isScanned) {
                                isScanned = true
                                coroutineScope.launch {
                                    delay(1000)
                                    qrContent = qrCode
                                }
                            }
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

    // Compute preview layout size and dynamic scaling
    val defaultScale = if (hasUltraWide && minZoomRatio < 1f) 1f / minZoomRatio else 1.0f
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

                val minScreenDim = minOf(screenWidth, screenHeight)
                val desiredScale = if (maxQrDim > 0) {
                    (minScreenDim * 0.4f) / maxQrDim
                } else {
                    defaultScale * 1.5f
                }

                // If ultra-wide is active, we apply a larger digital crop so the visual output matches 1x
                val cropScaleMultiplier = if (hasUltraWide && minZoomRatio < 1f) 1.4f / minZoomRatio else 1.3f
                targetScale = (desiredScale * cropScaleMultiplier).coerceIn(defaultScale, 2.5f)
                targetTransX = (screenWidth / 2f - qrCenterX) * targetScale
                targetTransY = (screenHeight / 2f - qrCenterY) * targetScale
            } else {
                targetScale = defaultScale
                targetTransX = 0f
                targetTransY = 0f
            }
        }

        val animScale by animateFloatAsState(targetScale, animationSpec = tween(300, easing = EaseInOutQuad), label = "animScale")
        val animTransX by animateFloatAsState(targetTransX, animationSpec = tween(300, easing = EaseInOutQuad), label = "animTransX")
        val animTransY by animateFloatAsState(targetTransY, animationSpec = tween(300, easing = EaseInOutQuad), label = "animTransY")

        // Render full-screen CameraX PreviewView
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

        // Scanning Reticle Overlay
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

        // Floating Back Navigation Button
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

        // Bottom Sheet showing QR content upon successful decode
        if (qrContent != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    qrContent = null
                    isScanned = false
                },
                containerColor = theme.sheetContainerColor,
                contentColor = theme.sheetContentColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Scanned Content",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.primaryTextColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = qrContent ?: "",
                        fontSize = 16.sp,
                        color = theme.primaryTextColor.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            qrContent = null
                            isScanned = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = theme.buttonColor)
                    ) {
                        Text("Scan Again", color = Color.White)
                    }
                }
            }
        }
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

    // Reticle pulse animation when a QR is detected
    val reticlePulse by infiniteTransition.animateFloat(
        initialValue = if (qrBox != null) 0.95f else 1.0f,
        targetValue = if (qrBox != null) 1.05f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "reticlePulse"
    )

    val laserPositionProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laserProgress"
    )

    val laserColor = theme.buttonColor
    val cornerColor = if (theme.iconBorderColor != Color.Transparent) theme.iconBorderColor else theme.buttonColor

    var targetLeft = 0f
    var targetTop = 0f
    var targetRight = 0f
    var targetBottom = 0f
    var hasTarget = false

    if (qrBox != null && imageSize != null) {
        val imgW = imageSize.first.toFloat()
        val imgH = imageSize.second.toFloat()

        val scaleX = if (rotation == 90 || rotation == 270) screenWidth / imgH else screenWidth / imgW
        val scaleY = if (rotation == 90 || rotation == 270) screenHeight / imgW else screenHeight / imgH

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
        val boxSize = baseBoxSize * reticlePulse
        targetLeft = (screenWidth - boxSize) / 2
        targetTop = (screenHeight - boxSize) / 2.5f
        targetRight = targetLeft + boxSize
        targetBottom = targetTop + boxSize
    }

    val animLeft by animateFloatAsState(targetValue = targetLeft, animationSpec = spring(stiffness = Spring.StiffnessLow), label = "animLeft")
    val animTop by animateFloatAsState(targetValue = targetTop, animationSpec = spring(stiffness = Spring.StiffnessLow), label = "animTop")
    val animRight by animateFloatAsState(targetValue = targetRight, animationSpec = spring(stiffness = Spring.StiffnessLow), label = "animRight")
    val animBottom by animateFloatAsState(targetValue = targetBottom, animationSpec = spring(stiffness = Spring.StiffnessLow), label = "animBottom")

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    ) {
        val cornerRadius = 24.dp.toPx()
        val boxWidth = animRight - animLeft
        val boxHeight = animBottom - animTop

        // Translucent layout overlay mask
        drawRect(
            color = Color.Black.copy(alpha = 0.6f),
            size = size
        )

        // Clear scanning window target
        drawRoundRect(
            color = Color.Transparent,
            topLeft = Offset(animLeft, animTop),
            size = Size(boxWidth, boxHeight),
            cornerRadius = CornerRadius(cornerRadius),
            blendMode = BlendMode.Clear
        )

        // Reticle corners
        val lineLength = 32.dp.toPx()
        val strokeWidth = 5.dp.toPx()

        // Top-Left
        val topLeftPath = Path().apply {
            moveTo(animLeft, animTop + lineLength)
            lineTo(animLeft, animTop + cornerRadius)
            quadraticTo(animLeft, animTop, animLeft + cornerRadius, animTop)
            lineTo(animLeft + lineLength, animTop)
        }
        drawPath(topLeftPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

        // Top-Right
        val topRightPath = Path().apply {
            moveTo(animRight - lineLength, animTop)
            lineTo(animRight - cornerRadius, animTop)
            quadraticTo(animRight, animTop, animRight, animTop + cornerRadius)
            lineTo(animRight, animTop + lineLength)
        }
        drawPath(topRightPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

        // Bottom-Left
        val bottomLeftPath = Path().apply {
            moveTo(animLeft, animBottom - lineLength)
            lineTo(animLeft, animBottom - cornerRadius)
            quadraticTo(animLeft, animBottom, animLeft + cornerRadius, animBottom)
            lineTo(animLeft + lineLength, animBottom)
        }
        drawPath(bottomLeftPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

        // Bottom-Right
        val bottomRightPath = Path().apply {
            moveTo(animRight, animBottom - lineLength)
            lineTo(animRight, animBottom - cornerRadius)
            quadraticTo(animRight, animBottom, animRight - cornerRadius, animBottom)
            lineTo(animRight - lineLength, animBottom)
        }
        drawPath(bottomRightPath, cornerColor, style = Stroke(strokeWidth, cap = StrokeCap.Round))

        // Moving laser line
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