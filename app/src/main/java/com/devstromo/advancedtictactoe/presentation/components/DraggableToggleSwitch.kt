package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun DraggableToggleSwitch(
    state: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val width = 100.dp
    val toggleWidth = 50.dp

    Box(
        modifier = modifier
            .width(width)
            .height(40.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(4.dp)
    ) {
        val offsetX = if (state.value) (width - toggleWidth - 4.dp) else 0.dp
        Box(
            modifier = Modifier
                .offset(x = with(LocalDensity.current) { offsetX.toPx() }.dp)
                .size(toggleWidth, 32.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp))
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val newValue = state.value xor (delta > 0)
                        state.value = newValue
                    }
                )
        ) {
            Text(
                text = if (state.value) "EN" else "ES",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
