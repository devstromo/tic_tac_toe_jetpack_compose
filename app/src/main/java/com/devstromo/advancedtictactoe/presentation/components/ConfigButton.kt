package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.ui.theme.AdvancedTicTacToeTheme

@Composable
fun ConfigButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    val typo = MaterialTheme.typography
    Box(
        modifier = modifier
            .size(70.dp)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(25)
            )
            .background(Color.Transparent)
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = text,
            style = typo.titleMedium.copy(
                color = Color.White
            ),
            overflow = TextOverflow.Visible,

            )
    }
}

@Preview
@Composable
private fun PreviewConfigButton() {
    AdvancedTicTacToeTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        ConfigButton(
            text = "EN",
            onClick = { }
        )
    }
}