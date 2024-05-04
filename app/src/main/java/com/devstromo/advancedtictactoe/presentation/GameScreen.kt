package com.devstromo.advancedtictactoe.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.presentation.components.PlayerMakerState
import com.devstromo.advancedtictactoe.presentation.components.PlayerMarker
import com.devstromo.advancedtictactoe.presentation.components.PlayerMarkerType

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GameScreen() {
    val typo = MaterialTheme.typography
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center,
        ) {

            Text(
                text = "Tic-Tac-Toe",
                style = typo.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            PlayerMarker(
                state = PlayerMakerState(
                    PlayerMarkerType.X, isSelected = true
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            PlayerMarker(
                state = PlayerMakerState(PlayerMarkerType.O)
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        BoardContent()
    }
}

@Composable
fun BoardContent() {
    val color = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 50.dp,
                horizontal = 45.dp,
            )
            .background(
                color = color.primary, shape = RoundedCornerShape(
                    15.dp
                )
            )
    ) {
        for (row in 1..3) {
            val positions = List(3) { col ->
                Pair(row, col + 1)
            }
            BoardRow(
                onItemSelected = { selectedPair ->
                    Log.i("Board", "Pair $selectedPair")
                },
                positions = positions
            )
        }
    }
}

@Composable
fun BoardRow(
    onItemSelected: (Pair<Int, Int>) -> Unit, positions: List<Pair<Int, Int>> = emptyList()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        positions.forEach { position ->
            BoardKeyBox(
                onItemSelected = { onItemSelected(position) },
            )
        }
    }
}

@Composable
fun BoardKeyBox(
    onItemSelected: () -> Unit,
) {
    val color = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(80.dp)
            .background(
                color = color.secondary, shape = RoundedCornerShape(
                    10.dp
                )
            )
            .clickable(
                onClick = onItemSelected,
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ),
    )
}