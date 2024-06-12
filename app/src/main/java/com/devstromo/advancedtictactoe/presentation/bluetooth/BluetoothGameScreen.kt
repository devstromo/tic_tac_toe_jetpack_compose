package com.devstromo.advancedtictactoe.presentation.bluetooth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import com.devstromo.advancedtictactoe.presentation.components.CustomButton
import com.devstromo.advancedtictactoe.presentation.permissions.RequestBluetoothPermissions

@Composable
fun BluetoothGameScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel,
) {
    var permissionsGranted by remember { mutableStateOf(false) }

    RequestBluetoothPermissions { granted ->
        permissionsGranted = granted
        if (!granted) {
            // Handle permission denied case if needed
        }
    }

    val isServerStarted by viewModel.isServerStarted.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()
    val scannedDevices by viewModel.scannedDevices.collectAsState()
    val pairedDevices by viewModel.pairedDevices.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startDiscovery()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 45.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        if (!permissionsGranted) {
            Text("Bluetooth permissions are required to proceed.")
        } else {
            if (isServerStarted) {
                Text("Server started, waiting for connection...")
            } else {
                Text("Nearby devices:")
                scannedDevices.forEach { device ->
                    Text("Device: ${device.name ?: "Unknown"} (${device.address})")
                    CustomButton(
                        text = "Connect",
                        onClick = {
                            viewModel.connectToDevice(device)
                        }
                    )
                }

                Text("Paired devices:")
                pairedDevices.forEach { device ->
                    Text("Device: ${device.name ?: "Unknown"} (${device.address})")
                    CustomButton(
                        text = "Connect",
                        onClick = {
                            viewModel.connectToDevice(device)
                        }
                    )
                }
            }

            if (isConnected) {
                Text("Connected to a device!")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            text = stringResource(R.string.bluetooth_game_create_title),
            onClick = {
                viewModel.startBluetoothServer()
            },
            isEnable = permissionsGranted
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(
            text = stringResource(R.string.bluetooth_game_join_title),
            onClick = {
                viewModel.startDiscovery()
            },
            isEnable = permissionsGranted
        )


    }
}
