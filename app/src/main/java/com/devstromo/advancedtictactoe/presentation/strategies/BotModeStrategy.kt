package com.devstromo.advancedtictactoe.presentation.strategies

import com.devstromo.advancedtictactoe.domain.GameModeStrategy
import com.devstromo.advancedtictactoe.domain.Player
import com.devstromo.advancedtictactoe.presentation.main.GameUiState
import com.devstromo.advancedtictactoe.presentation.main.GameViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BotModeStrategy : GameModeStrategy {
    override suspend fun onItemSelected(
        first: Int,
        second: Int,
        currentState: GameUiState,
        dispatcher: CoroutineDispatcher,
        viewModel: GameViewModel
    ): GameUiState {
        var newState = viewModel.makeMove(currentState, first, second, currentState.currentPlayer)
        newState = viewModel.applyAdvancedModeLogicIfNeeded(newState)

        if (viewModel.shouldBotMove(newState)) {
            val botMove = withContext(Dispatchers.Default) {
                viewModel.calculateBestMove(newState.board)
            }
            newState = viewModel.makeMove(newState, botMove.first, botMove.second, Player.PLAYER_2)
        }

        return newState
    }
}