package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.ui.theme.AdvancedTicTacToeTheme

@Composable
fun PlayerMarker(
    playerMarketType: PlayerMarkerType = PlayerMarkerType.X,
    itemsCount: Int = 0
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = if (PlayerMarkerType.X == playerMarketType)
                    "X"
                else
                    "O"

            )
        }
        Text(
            text = "$itemsCount"

        )

    }
}

@Preview
@Composable
private fun PlayerMarkerPreview() {
    AdvancedTicTacToeTheme {
        PlayerMarker()
    }
}