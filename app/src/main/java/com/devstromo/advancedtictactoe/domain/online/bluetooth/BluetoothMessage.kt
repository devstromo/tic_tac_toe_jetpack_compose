package com.devstromo.advancedtictactoe.domain.online.bluetooth

data class BluetoothMessage(
    val message: String,
    val senderName: String,
    val isFromLocalUser: Boolean,
)