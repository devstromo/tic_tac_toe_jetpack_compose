package com.devstromo.advancedtictactoe.presentation

import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.domain.GameModeStrategy
import com.devstromo.advancedtictactoe.domain.Player
import com.devstromo.advancedtictactoe.presentation.strategies.ClassicModeStrategy

data class GameUiState(
    val board: List<MutableList<Player>> = List(3) { MutableList(3) { Player.NONE } },
    val currentPlayer: Player = Player.PLAYER_1,
    val isGameOver: Boolean = false,
    val player1Moves: MutableList<Pair<Int, Int>> = mutableListOf(),
    val player2Moves: MutableList<Pair<Int, Int>> = mutableListOf(),
    val player1MoveCount: Int = 0,
    val player2MoveCount: Int = 0,
    val gameMode: GameMode = GameMode.CLASSIC,
    val nextMoveToRemove: Pair<Int, Int>? = null,
    val strategy: GameModeStrategy = ClassicModeStrategy()
)