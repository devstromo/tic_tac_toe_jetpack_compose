package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ConfigButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    val typo = MaterialTheme.typography
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(15),
        border = BorderStroke(2.dp, Color.White),
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = typo.bodyLarge.copy(
                color = Color.White
            ),
        )
    }
}