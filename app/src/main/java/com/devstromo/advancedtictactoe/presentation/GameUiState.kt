package com.devstromo.advancedtictactoe.presentation

import com.devstromo.advancedtictactoe.domain.CellState
import com.devstromo.advancedtictactoe.domain.Player

data class GameUiState(
    val isLoading: Boolean = true,
    val currentPlayer: Player = Player.PLAYER_1,
    val board: List<List<Player?>> = List(3) { MutableList(3) { null } },
)
