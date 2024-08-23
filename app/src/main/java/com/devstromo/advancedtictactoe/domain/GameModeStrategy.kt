package com.devstromo.advancedtictactoe.domain

import com.devstromo.advancedtictactoe.presentation.main.GameUiState
import com.devstromo.advancedtictactoe.presentation.main.GameViewModel
import kotlinx.coroutines.CoroutineDispatcher

fun interface GameModeStrategy {
    suspend fun onItemSelected(
        first: Int,
        second: Int,
        currentState: GameUiState,
        dispatcher: CoroutineDispatcher,
        viewModel: GameViewModel
    ): GameUiState
}