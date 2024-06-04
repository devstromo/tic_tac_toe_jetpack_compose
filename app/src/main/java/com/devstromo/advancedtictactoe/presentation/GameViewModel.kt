package com.devstromo.advancedtictactoe.presentation

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devstromo.advancedtictactoe.data.PlayerIconsGenerator.clearIcons
import com.devstromo.advancedtictactoe.data.PlayerIconsGenerator.generatePlayer1Icon
import com.devstromo.advancedtictactoe.data.PlayerIconsGenerator.generatePlayer2Icon
import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.domain.Player
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _state = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _state.asStateFlow()
    private var mediaPlayer: MediaPlayer? = null

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
    }

    fun updateGameMode(newGameMode: GameMode) {
        _state.update { currentState ->
            currentState.copy(
                gameMode = newGameMode
            )
        }
    }

    fun onItemSelected(first: Int, second: Int) {
        _state.update { currentState ->
            if (currentState.isGameOver) {
                return@update currentState
            }

            val cell = currentState.board[first][second]
            if (cell != Player.NONE) {
                return@update currentState
            }

            var newState = makeMove(currentState, first, second, currentState.currentPlayer)
            newState = applyAdvancedModeLogicIfNeeded(newState)

            if (shouldBotMove(newState)) {
                viewModelScope.launch {
                    val botMove = calculateBestMoveAsync(newState.board)
                    newState = makeMove(newState, botMove.first, botMove.second, Player.PLAYER_2)
                    _state.value = newState
                }
                return@update currentState
            }

            newState
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
                player2MoveCount = 0,
                nextMoveToRemove = null
            )
        }
    }

    fun player1Icon(): Int = generatePlayer1Icon()

    fun player2Icon(): Int = generatePlayer2Icon(_state.value.gameMode)

    fun resetIcons() {
        clearIcons()
    }

    fun canResetGame(): Boolean {
        return _state.value.board.any { row ->
            row.any { cell -> cell != Player.NONE }
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.setVolume(.2f, .2f)
        mediaPlayer?.start()
    }

    fun checkForWinner(board: List<List<Player>>): Boolean {
        val size = board.size

        for (i in 0 until size) {
            if (board[i][0] != Player.NONE && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true
            }
            if (board[0][i] != Player.NONE && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true
            }
        }

        if (board[0][0] != Player.NONE && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true
        }
        if (board[0][2] != Player.NONE && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true
        }

        return false
    }

    private fun applyAdvancedModeLogicIfNeeded(state: GameUiState): GameUiState {
        return if (state.gameMode == GameMode.ADVANCED && !state.isGameOver) {
            handleAdvancedMode(state)
        } else {
            state
        }
    }

    private fun shouldBotMove(state: GameUiState): Boolean {
        return state.gameMode == GameMode.BOT && !state.isGameOver && state.currentPlayer == Player.PLAYER_2
    }

    private fun makeMove(state: GameUiState, first: Int, second: Int, player: Player): GameUiState {
        val newBoard = state.board.toMutableList()
        newBoard[first] = newBoard[first].toMutableList().apply {
            this[second] = player
        }

        val updatedPlayer1Moves = state.player1Moves.toMutableList()
        val updatedPlayer2Moves = state.player2Moves.toMutableList()
        var updatedPlayer1MoveCount = state.player1MoveCount
        var updatedPlayer2MoveCount = state.player2MoveCount

        if (player == Player.PLAYER_1) {
            updatedPlayer1Moves.add(Pair(first, second))
            updatedPlayer1MoveCount++
        } else {
            updatedPlayer2Moves.add(Pair(first, second))
            updatedPlayer2MoveCount++
        }

        val newBoardAsList = newBoard.toList()
        val isGameOver = checkForWinner(newBoardAsList) || checkForFullBoard(newBoardAsList)

        val nextPlayer = if (!isGameOver) {
            if (state.currentPlayer == Player.PLAYER_1) Player.PLAYER_2 else Player.PLAYER_1
        } else state.currentPlayer

        return state.copy(
            board = newBoard,
            currentPlayer = nextPlayer,
            isGameOver = isGameOver,
            player1Moves = updatedPlayer1Moves,
            player2Moves = updatedPlayer2Moves,
            player1MoveCount = updatedPlayer1MoveCount,
            player2MoveCount = updatedPlayer2MoveCount
        )
    }

    private fun handleAdvancedMode(state: GameUiState): GameUiState {
        val newBoard = state.board.toMutableList()
        val updatedPlayer1Moves = state.player1Moves.toMutableList()
        val updatedPlayer2Moves = state.player2Moves.toMutableList()

        if (state.player1MoveCount > 3 && state.currentPlayer == Player.PLAYER_2) {
            val oldestMove = updatedPlayer1Moves.removeAt(0)
            newBoard[oldestMove.first][oldestMove.second] = Player.NONE
        }
        if (state.player2MoveCount > 3 && state.currentPlayer == Player.PLAYER_1) {
            val oldestMove = updatedPlayer2Moves.removeAt(0)
            newBoard[oldestMove.first][oldestMove.second] = Player.NONE
        }
        var nextMoveToRemove: Pair<Int, Int>? = null
        if (state.player1MoveCount > 2 && state.player2MoveCount > 2) {
            nextMoveToRemove = if (state.currentPlayer == Player.PLAYER_1) {
                updatedPlayer1Moves.firstOrNull()
            } else {
                updatedPlayer2Moves.firstOrNull()
            }
        }

        return state.copy(
            board = newBoard,
            player1Moves = updatedPlayer1Moves,
            player2Moves = updatedPlayer2Moves,
            nextMoveToRemove = nextMoveToRemove,
        )
    }

    private suspend fun calculateBestMoveAsync(board: List<MutableList<Player>>): Pair<Int, Int> {
        return withContext(dispatcher) {
            calculateBestMove(board)
        }
    }

    private fun calculateBestMove(board: List<MutableList<Player>>): Pair<Int, Int> {
        var bestMove = Pair(-1, -1)
        var bestValue = Int.MIN_VALUE

        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == Player.NONE) {
                    board[i][j] = Player.PLAYER_2
                    val moveValue = minimax(board, 0, false)
                    board[i][j] = Player.NONE
                    if (moveValue > bestValue) {
                        bestMove = Pair(i, j)
                        bestValue = moveValue
                    }
                }
            }
        }
        return bestMove
    }

    private fun minimax(board: List<MutableList<Player>>, depth: Int, isMaximizing: Boolean): Int {
        val winner = checkForWinner(board)
        if (winner) {
            return if (isMaximizing) -1 else 1
        }
        if (checkForFullBoard(board)) {
            return 0
        }

        return if (isMaximizing) {
            getMaximizedScore(board, depth)
        } else {
            getMinimizedScore(board, depth)
        }
    }

    private fun checkForFullBoard(board: List<List<Player>>): Boolean {
        return board.all { row -> row.all { it != Player.NONE } }
    }

    private fun getMaximizedScore(board: List<MutableList<Player>>, depth: Int): Int {
        var best = Int.MIN_VALUE
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == Player.NONE) {
                    board[i][j] = Player.PLAYER_2
                    best = maxOf(best, minimax(board, depth + 1, false))
                    board[i][j] = Player.NONE
                }
            }
        }
        return best
    }

    private fun getMinimizedScore(board: List<MutableList<Player>>, depth: Int): Int {
        var best = Int.MAX_VALUE
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == Player.NONE) {
                    board[i][j] = Player.PLAYER_1
                    best = minOf(best, minimax(board, depth + 1, true))
                    board[i][j] = Player.NONE
                }
            }
        }
        return best
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    internal fun updateStateForTesting(board: List<MutableList<Player>>) {
        _state.value = _state.value.copy(board = board)
    }
}
