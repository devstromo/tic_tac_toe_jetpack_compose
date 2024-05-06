package com.devstromo.advancedtictactoe.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.devstromo.advancedtictactoe.domain.CellState
import com.devstromo.advancedtictactoe.domain.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _state.asStateFlow()

    fun onItemSelected() {
        _state.update {
            it.copy(
                currentPlayer = if (it.currentPlayer == Player.PLAYER_1)
                    Player.PLAYER_2
                else
                    Player.PLAYER_1
            )
        }
    }

    fun checkForWinner(board: List<List<Player>>): Boolean {
        val size = board.size  // Assuming a 3x3 board here

        // Check rows and columns for a win
        for (i in 0 until size) {
            // Check if all cells in a row are the same and not NONE
            if (board[i][0] != Player.NONE && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true  // Winner found in a row
            }
            // Check if all cells in a column are the same and not NONE
            if (board[0][i] != Player.NONE && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true  // Winner found in a column
            }
        }

        // Check diagonals for a win
        // Main diagonal
        if (board[0][0] != Player.NONE && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true  // Winner found in the main diagonal
        }
        // Anti-diagonal
        if (board[0][2] != Player.NONE && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
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
