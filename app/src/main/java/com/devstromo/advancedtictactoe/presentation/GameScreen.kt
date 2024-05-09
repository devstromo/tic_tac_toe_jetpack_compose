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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.domain.Player
import com.devstromo.advancedtictactoe.presentation.components.CustomButton
import com.devstromo.advancedtictactoe.presentation.components.PlayerMakerState
import com.devstromo.advancedtictactoe.presentation.components.PlayerMarker
import com.devstromo.advancedtictactoe.presentation.components.PlayerMarkerType
import com.devstromo.advancedtictactoe.ui.theme.kPlayerOMarkColor
import com.devstromo.advancedtictactoe.ui.theme.kPlayerXMarkColor

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GameScreen(
    viewModel: GameViewModel,
    state: GameUiState,
    onItemSelected: (Int, Int) -> Unit,
) {
    val typo = MaterialTheme.typography
    val player1Moves = viewModel.countPlayer1Moves()
    val player2Moves = viewModel.countPlayer2Moves()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                    PlayerMarkerType.X,
                    isSelected = state.currentPlayer == Player.PLAYER_1,
                    itemsCount = player1Moves
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            PlayerMarker(
                state = PlayerMakerState(
                    PlayerMarkerType.O,
                    isSelected = state.currentPlayer == Player.PLAYER_2,
                    itemsCount = player2Moves
                )
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        BoardContent(
            onItemSelected,
            boardState = state.board
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 45.dp,
                )
        ) {
            CustomButton(
                text = "Restart"
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomButton(
                text = "Rules"
            )
        }
    }
}

@Composable
fun BoardContent(
    onItemSelected: (Int, Int) -> Unit,
    boardState: List<List<Player?>>,
    isGameOver: Boolean = false
) {
    val color = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 45.dp,
            )
            .background(
                color = color.primary, shape = RoundedCornerShape(
                    15.dp
                )
            )
    ) {
        for (row in 0..2) {
            val positions = List(3) { col ->
                Pair(row, col)
            }
            BoardRow(
                onItemSelected = { selectedPair ->
                    Log.i("Board", "Pair $selectedPair")
                    onItemSelected(selectedPair.first, selectedPair.second)
                },
                positions = positions,
                rowState = boardState[row],
                isGameOver = isGameOver
            )
        }
    }
}

@Composable
fun BoardRow(
    onItemSelected: (Pair<Int, Int>) -> Unit,
    positions: List<Pair<Int, Int>> = emptyList(),
    rowState: List<Player?>,
    isGameOver: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        positions.forEach { pair ->
            BoardKeyBox(
                onItemSelected = { onItemSelected(pair) },
                player = if (rowState[pair.second] == Player.NONE)
                    null
                else
                    rowState[pair.second],
                isClickable = !isGameOver
            )
        }
    }
}

@Composable
fun BoardKeyBox(
    onItemSelected: () -> Unit,
    player: Player? = null,
    isClickable: Boolean = true
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
                enabled = player == null && isClickable,
                onClick = onItemSelected,
                indication = rememberRipple(
                    bounded = true,
                    radius = 70.dp,
                    color = color.primary
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ),
    ) {
        Text(

            text = when (player) {
                Player.PLAYER_1 -> "X"
                Player.PLAYER_2 -> "O"
                else -> ""
            },
            style = MaterialTheme.typography.displayMedium.copy(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(top = 20.dp, start = 5.dp)
                .align(Alignment.Center),
            color = when (player) {
                Player.PLAYER_1 -> kPlayerXMarkColor
                Player.PLAYER_2 -> kPlayerOMarkColor
                else -> Color.Transparent
            }
        )
    }
}