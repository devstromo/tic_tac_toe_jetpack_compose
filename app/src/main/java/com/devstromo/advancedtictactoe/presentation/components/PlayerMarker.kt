package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.config.extensions.modifyIf
import com.devstromo.advancedtictactoe.ui.theme.AdvancedTicTacToeTheme
import com.devstromo.advancedtictactoe.ui.theme.kMainDarkThemeColor
import com.devstromo.advancedtictactoe.ui.theme.kPlayerOMarkColor
import com.devstromo.advancedtictactoe.ui.theme.kPlayerXMarkColor

@Composable
fun PlayerMarker(
    state: PlayerMakerState = PlayerMakerState(),
    playerIconId: Int = -1,
) {
    val typo = MaterialTheme.typography
    val shape = RoundedCornerShape(
        topStart = 50.dp,
        topEnd = 50.dp,
        bottomEnd = 20.dp,
        bottomStart = 20.dp
    )

    val roundedShape = RoundedCornerShape(50.dp)
    val boxSize = 80.dp
    val drawable = painterResource(id = playerIconId)
    val isPlayerOne = PlayerMarkerType.X == state.playerMarketType

    Box(
        modifier = Modifier
            .width(boxSize)
            .height(160.dp)
            .clip(shape)
            .background(color = kMainDarkThemeColor)
            .modifyIf(state.isSelected) {
                border(
                    width = 1.dp,
                    color = Color.White,
                    shape
                )
            }
    ) {
        Image(
            painter = drawable,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(boxSize)
                .clip(roundedShape)
                .modifyIf(state.isSelected) {
                    border(
                        width = 1.dp,
                        color = Color.White,
                        roundedShape
                    )
                }
        )
        Text(
            modifier = Modifier
                .padding(top = 55.dp)
                .align(Alignment.Center),
            text = if (isPlayerOne) "X" else "O",
            style = typo.titleLarge.copy(
                color = if (isPlayerOne) kPlayerXMarkColor else kPlayerOMarkColor,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 2.dp),
            text = "${state.itemsCount}",
            style = typo.bodySmall.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
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