package com.devstromo.advancedtictactoe.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.domain.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _state.asStateFlow()

    fun updateGameMode(newGameMode: GameMode) {
        _state.update { currentState ->
            currentState.copy(gameMode = newGameMode)
        }
    }

    fun onItemSelected(first: Int, second: Int) {
        _state.update { currentState ->
            if (currentState.isGameOver) {
                currentState  // If game is over, do not allow any changes
            } else {
                val cell = currentState.board[first][second]
                if (cell == Player.NONE) {  // Only allow changes if cell is empty
                    val newBoard = currentState.board.toMutableList()
                    newBoard[first] = newBoard[first].toMutableList().apply {
                        this[second] = currentState.currentPlayer
                    }

                    // Update move count and moves list
                    val updatedPlayer1Moves = currentState.player1Moves.toMutableList()
                    val updatedPlayer2Moves = currentState.player2Moves.toMutableList()
                    var updatedPlayer1MoveCount = currentState.player1MoveCount
                    var updatedPlayer2MoveCount = currentState.player2MoveCount

                    if (currentState.currentPlayer == Player.PLAYER_1) {
                        updatedPlayer1Moves.add(Pair(first, second))
                        updatedPlayer1MoveCount++
                        if (currentState.gameMode == GameMode.ADVANCED && updatedPlayer1MoveCount > 3) {
                            val oldestMove = updatedPlayer1Moves.removeAt(0)
                            newBoard[oldestMove.first][oldestMove.second] = Player.NONE
                        }
                    } else {
                        updatedPlayer2Moves.add(Pair(first, second))
                        updatedPlayer2MoveCount++
                        if (currentState.gameMode == GameMode.ADVANCED && updatedPlayer2MoveCount > 3) {
                            val oldestMove = updatedPlayer2Moves.removeAt(0)
                            newBoard[oldestMove.first][oldestMove.second] = Player.NONE
                        }
                    }

                    // Check if the move results in a win immediately after the move
                    val isGameOver = checkForWinner(newBoard.toList())

                    // Update player before setting game over state
                    val nextPlayer = if (!isGameOver) {
                        if (currentState.currentPlayer == Player.PLAYER_1) Player.PLAYER_2 else Player.PLAYER_1
                    } else currentState.currentPlayer  // Do not switch players if the game is over

                    currentState.copy(
                        board = newBoard,
                        currentPlayer = nextPlayer,
                        isGameOver = isGameOver,
                        player1Moves = updatedPlayer1Moves,
                        player2Moves = updatedPlayer2Moves,
                        player1MoveCount = updatedPlayer1MoveCount,
                        player2MoveCount = updatedPlayer2MoveCount
                    )
                } else {
                    currentState  // If the cell is not empty, do not update
                }
            }
        }
    }

    fun resetGame() {
        _state.update { currentState ->
            currentState.copy(
                board = List(3) { MutableList(3) { Player.NONE } },
                currentPlayer = Player.PLAYER_1,
                isGameOver = false,
                player1Moves = mutableListOf(),
                player2Moves = mutableListOf(),
                player1MoveCount = 0,
                player2MoveCount = 0
            )
        }
    }

    fun canResetGame(): Boolean {
        return _state.value.board
            .any { players ->
                players.any { player -> player != Player.NONE }
            }
    }

    // Method to count Player 1's moves
    fun countPlayer1Moves(): Int {
        return countPlayerMoves(Player.PLAYER_1)
    }

    // Method to count Player 2's moves
    fun countPlayer2Moves(): Int {
        return countPlayerMoves(Player.PLAYER_2)
    }

    // Helper method to count moves for a specific player
    private fun countPlayerMoves(player: Player): Int {
        val board = _state.value.board
        var count = 0
        board.forEach { row ->
            row.forEach { cell ->
                if (cell == player) {
                    count++
                }
            }
        }
        return count
    }

    fun checkForWinner(board: List<List<Player>>): Boolean {
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

    private fun checkForFullBoard(board: List<MutableList<Player?>>): Boolean {
        return board.all { row -> row.all { it != Player.NONE } }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    internal fun updateStateForTesting(board: List<MutableList<Player>>) {
        _state.value = _state.value.copy(board = board)
    }
}
