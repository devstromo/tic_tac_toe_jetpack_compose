package com.devstromo.advancedtictactoe.presentation.components

data class PlayerMakerState(
    val playerMarketType: PlayerMarkerType = PlayerMarkerType.X,
    val itemsCount: Int = 0,
    val isSelected: Boolean = false
)