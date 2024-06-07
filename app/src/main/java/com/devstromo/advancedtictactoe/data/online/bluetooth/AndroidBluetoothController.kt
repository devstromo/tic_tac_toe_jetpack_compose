package com.devstromo.advancedtictactoe.data.online.bluetooth

import android.bluetooth.BluetoothManager
import android.content.Context
import com.devstromo.advancedtictactoe.domain.online.bluetooth.BluetoothController
import com.devstromo.advancedtictactoe.domain.online.bluetooth.BluetoothDevice
import com.devstromo.advancedtictactoe.domain.online.bluetooth.ConnectionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class AndroidBluetoothController(
    private val context: Context
) : BluetoothController {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }
    override val isConnected: StateFlow<Boolean>
        get() = TODO("Not yet implemented")
    override val scannedDevices: StateFlow<List<BluetoothDevice>>
        get() = TODO("Not yet implemented")
    override val pairedDevices: StateFlow<List<BluetoothDevice>>
        get() = TODO("Not yet implemented")
    override val errors: SharedFlow<String>
        get() = TODO("Not yet implemented")

    override fun startDiscovery() {
        TODO("Not yet implemented")
    }

    override fun stopDiscovery() {
        TODO("Not yet implemented")
    }

    override fun startBluetoothServer(): Flow<ConnectionResult> {
        TODO("Not yet implemented")
    }

    override fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult> {
        TODO("Not yet implemented")
    }

    override fun closeConnection() {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }
}