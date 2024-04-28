package com.devstromo.advancedtictactoe.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameUiState())
    val uiState = _state.asStateFlow()
}