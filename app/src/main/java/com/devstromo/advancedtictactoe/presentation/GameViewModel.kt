package com.devstromo.advancedtictactoe.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.devstromo.advancedtictactoe.domain.CellState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameUiState())
    val uiState = _state.asStateFlow()

    fun checkForWinner(): Boolean {
        val board = _state.value.board
        val size = board.size  // Assuming a 3x3 board here

        // Check rows and columns
        for (i in 0 until size) {
            if (board[i][0] != CellState.CLEAR && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true  // Winner found in a row
            }
            if (board[0][i] != CellState.CLEAR && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true  // Winner found in a column
            }
        }

        // Check diagonals
        if (board[0][0] != CellState.CLEAR && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true  // Winner found in the main diagonal
        }
        if (board[0][2] != CellState.CLEAR && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true  // Winner found in the anti-diagonal
        }

        return false  // No winner found
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    internal fun updateStateForTesting(board: Array<Array<CellState>>) {
        val state = GameUiState(isLoading = false, board = board)
        _state.value = state
    }
}
