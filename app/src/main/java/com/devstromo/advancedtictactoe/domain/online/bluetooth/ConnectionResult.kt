package com.devstromo.advancedtictactoe.domain.online.bluetooth

sealed interface ConnectionResult {
    data object ConnectionEstablished : ConnectionResult

    data class Error(val message: String) : ConnectionResult

    data class TransferSucceeded(val message: BluetoothMessage): ConnectionResult
}