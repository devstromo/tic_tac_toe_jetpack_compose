package com.devstromo.advancedtictactoe.data.item

data class MenuItem(
    val title: String,
    val onClick: () -> Unit,
    val isActive: Boolean = false,
)