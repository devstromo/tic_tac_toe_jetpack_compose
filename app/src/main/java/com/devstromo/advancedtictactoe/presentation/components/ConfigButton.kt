package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.ui.theme.AdvancedTicTacToeTheme
import com.devstromo.advancedtictactoe.ui.theme.kSecondaryDarkThemeColor

@Composable
fun ConfigButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    val typo = MaterialTheme.typography
    val color = MaterialTheme.colorScheme
    val rippleEffectColor = color.secondary.copy(
        alpha = 0.3f
    )
    Box(
        modifier = modifier
            .size(70.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(25)
            )
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = rippleEffectColor
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = text,
            style = typo.titleMedium.copy(
                color = color.secondary
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