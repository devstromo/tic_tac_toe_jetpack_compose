package com.devstromo.advancedtictactoe.domain

import com.devstromo.advancedtictactoe.presentation.GameUiState
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import kotlinx.coroutines.CoroutineDispatcher

interface GameModeStrategy {
    suspend fun onItemSelected(
        first: Int,
        second: Int,
        currentState: GameUiState,
        dispatcher: CoroutineDispatcher,
        viewModel: GameViewModel
    ): GameUiState
}