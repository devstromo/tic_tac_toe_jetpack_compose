package com.devstromo.advancedtictactoe.presentation.strategies

import com.devstromo.advancedtictactoe.domain.GameModeStrategy
import com.devstromo.advancedtictactoe.presentation.main.GameUiState
import com.devstromo.advancedtictactoe.presentation.main.GameViewModel
import kotlinx.coroutines.CoroutineDispatcher

class ClassicModeStrategy : GameModeStrategy {
    override suspend fun onItemSelected(
        first: Int,
        second: Int,
        currentState: GameUiState,
        dispatcher: CoroutineDispatcher,
        viewModel: GameViewModel
    ): GameUiState {
        return viewModel.makeMove(currentState, first, second, currentState.currentPlayer)
    }
}