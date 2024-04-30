package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.ui.theme.AdvancedTicTacToeTheme
import com.devstromo.advancedtictactoe.ui.theme.kMainDarkThemeColor

@Composable
fun PlayerMarker(
    state: PlayerMakerState = PlayerMakerState()
) {
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(80.dp)
            .background(
                color = kMainDarkThemeColor,
                shape = RoundedCornerShape(
                    topStart = 50.dp,
                    topEnd = 50.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
            )
            .border(
                width = 1.dp,
                color = Color.White,
                RoundedCornerShape(
                    topStart = 50.dp,
                    topEnd = 50.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                ),
            )
    ) {
        Box {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Color.Red,
                        shape = RoundedCornerShape(
                            50.dp
                        )
                    )
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = if (PlayerMarkerType.X == state.playerMarketType)
                    "X"
                else
                    "O",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            text = "${state.itemsCount}",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        )
    }

}

@Preview
@Composable
private fun PlayerMarkerPreview(
    @PreviewParameter(PlayMakerPreviewParameterProvider::class) state: PlayerMakerState
) {
    AdvancedTicTacToeTheme {
        PlayerMarker(state)
    }
}

class PlayMakerPreviewParameterProvider : PreviewParameterProvider<PlayerMakerState> {
    override val values = sequenceOf(
        PlayerMakerState(),
        PlayerMakerState(
            playerMarketType = PlayerMarkerType.O,
            itemsCount = 2,
        ),
        PlayerMakerState(
            playerMarketType = PlayerMarkerType.O,
            itemsCount = 2,
            isSelected = true
        ),
    )
}