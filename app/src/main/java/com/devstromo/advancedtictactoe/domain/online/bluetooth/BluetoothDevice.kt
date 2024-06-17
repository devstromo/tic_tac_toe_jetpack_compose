package com.devstromo.advancedtictactoe.domain.online.bluetooth

typealias BluetoothDeviceDomain = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String,
    val rssi: Int = 0
)