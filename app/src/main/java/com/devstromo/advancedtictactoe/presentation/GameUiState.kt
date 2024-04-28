package com.devstromo.advancedtictactoe.presentation

import com.devstromo.advancedtictactoe.domain.CellState
import com.devstromo.advancedtictactoe.domain.Player

data class GameUiState(
    val isLoading: Boolean = true,
    val currentPlayer: Player = Player.PLAYER_1,
    val board: Array<Array<CellState>> = Array(3) {
        Array(
            3
        ) { CellState.CLEAR }
    },
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameUiState

        if (isLoading != other.isLoading) return false
        if (currentPlayer != other.currentPlayer) return false
        if (!board.contentDeepEquals(other.board)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + currentPlayer.hashCode()
        result = 31 * result + board.contentDeepHashCode()
        return result
    }

}
