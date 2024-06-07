package com.devstromo.advancedtictactoe.data.online.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.devstromo.advancedtictactoe.domain.online.bluetooth.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}