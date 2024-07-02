package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreenMenu(
    modifier: Modifier = Modifier
) {

}

internal data class MenuItem(
    val title: String,
    val onClick: () -> Unit
)