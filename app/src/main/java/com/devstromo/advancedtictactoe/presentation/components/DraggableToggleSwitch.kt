package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.ui.theme.AdvancedTicTacToeTheme

@Composable
fun DraggableToggleSwitch(
    state: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val width = 100.dp

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
                        color = if (!state.value) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable(
                        onClick = { state.value = false },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            color = Color.White
                        )
                    ) ,
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
                        color = if (state.value) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable(
                        onClick = { state.value = true },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            color = Color.White
                        )
                    ),
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

@Composable
@Preview(showBackground = true)
fun DraggableToggleSwitchPreview() {
    AdvancedTicTacToeTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        val toggleState = remember { mutableStateOf(false) }
        DraggableToggleSwitch(state = toggleState)
    }

}
