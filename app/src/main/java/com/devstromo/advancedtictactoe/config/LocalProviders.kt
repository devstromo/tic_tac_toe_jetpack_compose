package com.devstromo.advancedtictactoe.config

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf

val LocalAppLanguage = compositionLocalOf<MutableState<String>> { error("No language provided") }
