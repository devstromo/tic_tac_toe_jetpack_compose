package com.devstromo.advancedtictactoe.navigation

import com.devstromo.advancedtictactoe.domain.GameMode

sealed class Screen(val route: String) {
    data object Initial: Screen("initial_screen")
    data object Game: Screen("game_screen/{gameMode}") {
        fun createRoute(gameMode: GameMode): String {
            return "game_screen/${gameMode.name}"
        }
    }
    data object Rules: Screen("rules_screen")

    data object Bluetooth: Screen("bluetooth_screen")

    data object QRScanner: Screen("qr_code_scanner_screen")

    data object Help: Screen("help_screen")
}