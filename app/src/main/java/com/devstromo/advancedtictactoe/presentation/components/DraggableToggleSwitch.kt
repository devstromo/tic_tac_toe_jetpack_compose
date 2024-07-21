package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(
                        if (!state.value) MaterialTheme.colorScheme.primary else Color.Transparent,
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { state.value = false },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ES",
                    color = if (!state.value) Color.White else Color.Black
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(
                        if (state.value) MaterialTheme.colorScheme.primary else Color.Transparent,
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { state.value = true },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "EN",
                    color = if (state.value) Color.White else Color.Black
                )
            }
        }
    }
}
