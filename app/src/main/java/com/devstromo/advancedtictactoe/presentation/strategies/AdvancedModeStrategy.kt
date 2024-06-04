package com.devstromo.advancedtictactoe.presentation.strategies

import com.devstromo.advancedtictactoe.domain.GameModeStrategy
import com.devstromo.advancedtictactoe.presentation.GameUiState
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import kotlinx.coroutines.CoroutineDispatcher

class AdvancedModeStrategy : GameModeStrategy {
    override suspend fun onItemSelected(
        first: Int,
        second: Int,
        currentState: GameUiState,
        dispatcher: CoroutineDispatcher,
        viewModel: GameViewModel
    ): GameUiState {
        val newState = viewModel.makeMove(currentState, first, second, currentState.currentPlayer)
        return viewModel.applyAdvancedModeLogicIfNeeded(newState)
    }
}