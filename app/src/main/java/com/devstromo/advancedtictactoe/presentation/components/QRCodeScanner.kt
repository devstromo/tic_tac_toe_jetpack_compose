package com.devstromo.advancedtictactoe.presentation.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.devstromo.advancedtictactoe.domain.online.bluetooth.BluetoothDeviceDomain
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import com.devstromo.advancedtictactoe.presentation.permissions.RequestCameraPermission
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

@Composable
fun QRCodeScreen(
    viewModel: GameViewModel
) {
    var scannedCode by remember { mutableStateOf<String?>(null) }

    var hasCameraPermission by remember { mutableStateOf(false) }

    if (hasCameraPermission) {
        QRCodeScanner(
            scannedResult = {
                scannedCode = it
                val parts = it.split(";")
                val deviceName = parts[0].split(":")[1]
                val deviceAddress = parts[1].split(":")[1]
                Log.i("QRCodeScreen", "QRCodeScreen: parts: ${parts[0]} and ${parts[1]} device name: $deviceName device address: $deviceAddress ")
//                viewModel.connectToDevice(
//                    BluetoothDeviceDomain(
//                        name = deviceName,
//                        address = deviceAddress,
//                    )
//                )
            }
        )
        scannedCode?.let {
            AlertDialog(
                onDismissRequest = { scannedCode = null },
                title = { Text("QR Code Scanned") },
                text = { Text(it) },
                confirmButton = {
                    Button(onClick = { scannedCode = null }) {
                        Text("OK")
                    }
                }
            )
        }
    } else {
        RequestCameraPermission { isGranted ->
            hasCameraPermission = isGranted
        }
    }
}

@Composable
fun QRCodeScanner(
    scannedResult: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cornerColors = Color.White
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val density = LocalDensity.current

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

    Box(modifier = Modifier.fillMaxSize()) {
        if (hasCameraPermission) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    val previewView = PreviewView(context)
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageAnalyzer = ImageAnalysis.Builder().build().also {
                        it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                            with(density) {
                                processImageProxy(
                                    imageProxy = imageProxy,
                                    sizePx = 230.dp.toPx(),
                                    onQRCodeScanned = scannedResult
                                )
                            }
                        }
                    }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalyzer
                        )
                    } catch (exc: Exception) {
                        // Handle any errors (e.g., binding the camera failed)
                    }

                    previewView
                },
                update = {}
            )

            // Overlay with a transparent square and corner markers
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x20000000)) // full overlay
                    .drawWithContent {
                        drawContent()
                        val size = Size(230.dp.toPx(), 230.dp.toPx())
                        val topLeft = Offset(
                            (this.size.width - size.width) / 2,
                            (this.size.height - size.height) / 2
                        )
                        val path = Path().apply {
                            addRect(
                                Rect(
                                    0f,
                                    0f,
                                    this@drawWithContent.size.width,
                                    this@drawWithContent.size.height
                                )
                            )
                            addRect(
                                Rect(
                                    topLeft.x,
                                    topLeft.y,
                                    topLeft.x + size.width,
                                    topLeft.y + size.height
                                )
                            )
                            fillType = PathFillType.EvenOdd
                        }
                        drawPath(path, Color(0x80000000)) // outer overlay to center square
                        // Drawing corner markers
                        drawRect(
                            cornerColors, topLeft, Size(20.dp.toPx(), 4.dp.toPx())
                        )
                        drawRect(
                            cornerColors, topLeft, Size(4.dp.toPx(), 20.dp.toPx())
                        )
                        drawRect(
                            cornerColors,
                            Offset(topLeft.x + size.width - 20.dp.toPx(), topLeft.y),
                            Size(20.dp.toPx(), 4.dp.toPx())
                        )
                        drawRect(
                            cornerColors,
                            Offset(topLeft.x + size.width - 4.dp.toPx(), topLeft.y),
                            Size(4.dp.toPx(), 20.dp.toPx())
                        )
                        drawRect(
                            cornerColors,
                            Offset(topLeft.x, topLeft.y + size.height - 4.dp.toPx()),
                            Size(20.dp.toPx(), 4.dp.toPx())
                        )
                        drawRect(
                            cornerColors,
                            Offset(topLeft.x, topLeft.y + size.height - 20.dp.toPx()),
                            Size(4.dp.toPx(), 20.dp.toPx())
                        )
                        drawRect(
                            cornerColors,
                            Offset(
                                topLeft.x + size.width - 20.dp.toPx(),
                                topLeft.y + size.height - 4.dp.toPx()
                            ),
                            Size(20.dp.toPx(), 4.dp.toPx())
                        )
                        drawRect(
                            cornerColors,
                            Offset(
                                topLeft.x + size.width - 4.dp.toPx(),
                                topLeft.y + size.height - 20.dp.toPx()
                            ),
                            Size(4.dp.toPx(), 20.dp.toPx())
                        )
                    }
            )
        } else {
            RequestCameraPermission { granted ->
                hasCameraPermission = granted
            }
        }
    }
}

private fun processImageProxy(
    imageProxy: ImageProxy,
    sizePx: Float,
    onQRCodeScanned: (String) -> Unit
) {
    val buffer: ByteBuffer = imageProxy.planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)

    // Ensure the size is within the bounds of the image
    val width = imageProxy.width
    val height = imageProxy.height
    val cropSize = minOf(sizePx.toInt(), width, height)
    val cropLeft = (width - cropSize) / 2
    val cropTop = (height - cropSize) / 2

    val source = PlanarYUVLuminanceSource(
        bytes,
        width,
        height,
        cropLeft,
        cropTop,
        cropSize,
        cropSize,
        false
    )
    val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
    try {
        val result = MultiFormatReader().decode(binaryBitmap)
        onQRCodeScanned(result.text)
    } catch (e: NotFoundException) {
        // QR code not found
    } finally {
        imageProxy.close()
    }
}

