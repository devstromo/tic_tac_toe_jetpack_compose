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


    fun onItemSelected(first: Int, second: Int) {
        _state.update { currentState ->
            if (currentState.board[first][second] == Player.NONE) {
                // The cell is empty, proceed to mark it and switch players.
                val newBoard = currentState.board.mapIndexed { rowIndex, row ->
                    if (rowIndex == first) {
                        row.toMutableList().apply {
                            this[second] = currentState.currentPlayer
                        }
                    } else {
                        row.toList()  // Just convert to a new List to avoid mutation
                    }
                }

                // Switch current player
                val nextPlayer =
                    if (currentState.currentPlayer == Player.PLAYER_1) Player.PLAYER_2 else Player.PLAYER_1

                // Update the state with the new board and next player
                currentState.copy(
                    board = newBoard,
                    currentPlayer = nextPlayer
                )
            } else {
                // The cell is not empty, do not update the board or switch players.
                currentState
            }
        }
    }

    fun checkForWinner(): Boolean {
        val board = _state.value.board
        val size = board.size

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
    internal fun updateStateForTesting(board: List<List<Player>>) {
        _state.value = _state.value.copy(board = board)
    }
}
