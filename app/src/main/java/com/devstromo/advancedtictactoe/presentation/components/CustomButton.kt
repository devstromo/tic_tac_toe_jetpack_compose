package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.ui.theme.kSecondaryDarkThemeColor

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit,
    isEnable: Boolean = true,
) {
    val typo = MaterialTheme.typography
    Button(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(25),
        onClick = onClick,
        enabled = isEnable,
        colors = ButtonDefaults.buttonColors(
            containerColor = kSecondaryDarkThemeColor,
        )
    ) {
        if (text.isNotBlank()) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 10.dp),
                text = text,
                textAlign = TextAlign.Center,
                style = typo.bodyLarge.copy(
                    color = Color.White
                )
            )
        }
    }

}