package com.devstromo.advancedtictactoe.presentation.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import com.devstromo.advancedtictactoe.presentation.components.CustomButton
import com.devstromo.advancedtictactoe.presentation.components.QrCodeImage
import com.devstromo.advancedtictactoe.presentation.permissions.RequestBluetoothPermissions
import com.devstromo.advancedtictactoe.presentation.permissions.hasBluetoothPermissions

@Composable
fun BluetoothGameScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel,
) {
    val typo = MaterialTheme.typography
    val context = LocalContext.current
    var permissionsGranted by remember { mutableStateOf(hasBluetoothPermissions(context)) }
    var isBluetoothEnabled by remember { mutableStateOf(isBluetoothEnabled()) }
    var showEnableBluetoothDialog by remember { mutableStateOf(false) }

    var startServer by remember { mutableStateOf(false) }
    var joinGameServer by remember { mutableStateOf(false) }

    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        isBluetoothEnabled = isBluetoothEnabled()
    }

    if (!permissionsGranted) {
        RequestBluetoothPermissions { granted ->
            permissionsGranted = granted
        }
    }

    val isServerStarted by viewModel.isServerStarted.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()

    if (showEnableBluetoothDialog) {
        AlertDialog(
            onDismissRequest = { showEnableBluetoothDialog = false },
            title = { Text("Enable Bluetooth") },
            text = { Text("Bluetooth is required to proceed. Please enable Bluetooth.") },
            confirmButton = {
                Button(modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    onClick = {
                        showEnableBluetoothDialog = false
                        enableBluetoothLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
                    }) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 10.dp),
                        text = stringResource(R.string.bluetooth_connection_enable_request),
                        textAlign = TextAlign.Center,
                        style = typo.bodyLarge.copy(color = Color.White)
                    )
                }
            },
            dismissButton = {
                Button(modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    onClick = {
                        showEnableBluetoothDialog = false
                    }) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 10.dp),
                        text = stringResource(R.string.bluetooth_connection_cancel_request),
                        textAlign = TextAlign.Center,
                        style = typo.bodyLarge.copy(color = Color.White)
                    )
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.TopStart)
        ) {
            IconButton(
                modifier = Modifier
                    .height(45.dp)
                    .width(45.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(25)
                    ),

                onClick = { navController.popBackStack() },
            ) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = "Favorite",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 45.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))
            if (isServerStarted) {
                Text("Server started, waiting for connection...")
            } else {
                if (!permissionsGranted) {
                    Text("Bluetooth permissions are required to proceed.")
                } else if (!isBluetoothEnabled) {
                    Text("Need to turn on your bluetooth connection.")
                } else if (startServer) {
                    QrCodeImage(
                        content = "Test Code",
                        size = 200.dp
                    )
                } else if (joinGameServer) {
                    Text("Join Game")
                } else {
                    Text("Create or Join to a new Game")
                }
            }

            if (isConnected) {
                Text("Connected to a device!")
            }

            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                text = stringResource(R.string.bluetooth_game_create_title),
                onClick = {
                    if (permissionsGranted && !isBluetoothEnabled) {
                        showEnableBluetoothDialog = true
                    } else {
                        //                    viewModel.startBluetoothServer()
                        startServer = true
                    }
                },
                isEnable = permissionsGranted && isBluetoothEnabled
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomButton(
                text = stringResource(R.string.bluetooth_game_join_title),
                onClick = {
                    navController.navigate(Screen.QRScanner.route)
                    //                viewModel.startDiscovery()
                },
                isEnable = permissionsGranted && isBluetoothEnabled
            )
        }
    }
}

fun isBluetoothEnabled(): Boolean {
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    return bluetoothAdapter?.isEnabled == true
}
