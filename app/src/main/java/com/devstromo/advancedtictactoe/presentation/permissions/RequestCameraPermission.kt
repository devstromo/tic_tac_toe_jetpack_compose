package com.devstromo.advancedtictactoe.presentation.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestCameraPermission(onPermissionResult: (Boolean) -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        onPermissionResult(isGranted)
    }

    val showPermissionRationale = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        )
    }

    if (showPermissionRationale.value) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Camera Permission") },
            text = { Text("This app needs access to your camera to scan QR codes.") },
            confirmButton = {
                Button(onClick = {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }) {
                    Text("Allow")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showPermissionRationale.value = false
                }) {
                    Text("Deny")
                }
            }
        )
    }
}
