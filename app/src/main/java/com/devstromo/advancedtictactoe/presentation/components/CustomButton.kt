package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    val typo = MaterialTheme.typography
    Button(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.Red,
                shape = RoundedCornerShape(
                    10.dp
                )
            ),
        onClick = { /*TODO*/ }
    ) {
        if (text.isNotBlank()) {
            Text(
                text = text,
                style = typo.bodyMedium
            )
        }
    }

}