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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
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
        Box(
            modifier = Modifier
                .drawWithCache {
                    val roundedPolygon = RoundedPolygon(
                        numVertices = 4,
                        radius = size.minDimension / 2,
                        centerX = size.width / 2,
                        centerY = size.height / 2,
                        rounding = CornerRounding(
                            size.minDimension / 10f,
                            smoothing = 0.1f
                        )
                    )
                    val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
                    onDrawBehind {
                        drawPath(roundedPolygonPath, color = Color.Black)
                    }
                }
                .graphicsLayer {
                    rotationY = 35.0f
                    rotationX = 12.0f
                    cameraDistance = 12.dp.toPx()
                }
                .size(100.dp)
        )
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