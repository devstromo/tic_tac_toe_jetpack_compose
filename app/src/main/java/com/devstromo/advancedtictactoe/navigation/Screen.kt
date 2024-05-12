package com.devstromo.advancedtictactoe.navigation

sealed class Screen(val route: String) {
    data object Initial: Screen("initial_screen")
    data object Game: Screen("game_screen")
}