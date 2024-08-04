package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RuleInfo(
    modifier: Modifier = Modifier,
    header: String,
    subtitle: String,
    iconId: Int,
    iconSize: Dp = 28.dp
) {
    val typos = MaterialTheme.typography
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .padding(end = 10.dp)
                .weight(1f),
            painter = painterResource(
                id = iconId
            ),
            contentDescription = "",
            tint = Color.Unspecified
        )
        Column(
            modifier = Modifier.weight(3.5f)
        ) {
            Text(
                text = header,
                style = typos.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Text(
                text = subtitle,
                style = typos.labelSmall
            )
        }
        Box(
            modifier = Modifier
                .weight(1.5f)
                .background(color = Color.Red)
        ) {

        }
    }
}