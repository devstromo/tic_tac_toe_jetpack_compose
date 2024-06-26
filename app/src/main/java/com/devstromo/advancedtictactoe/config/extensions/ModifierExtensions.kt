package com.devstromo.advancedtictactoe.config.extensions

import androidx.compose.ui.Modifier

fun Modifier.modifyIf(condition: Boolean, modify: Modifier.() -> Modifier) =
    if (condition) modify() else this