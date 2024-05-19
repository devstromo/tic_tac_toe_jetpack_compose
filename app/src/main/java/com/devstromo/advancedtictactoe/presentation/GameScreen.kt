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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.domain.Player
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.components.CustomButton
import com.devstromo.advancedtictactoe.presentation.components.PlayerMakerState
import com.devstromo.advancedtictactoe.presentation.components.PlayerMarker
import com.devstromo.advancedtictactoe.presentation.components.PlayerMarkerType
import com.devstromo.advancedtictactoe.ui.theme.kPlayerOMarkColor
import com.devstromo.advancedtictactoe.ui.theme.kPlayerXMarkColor
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel,
    state: GameUiState,
    onItemSelected: (Int, Int) -> Unit,
) {
    val typo = MaterialTheme.typography
    val color = MaterialTheme.colorScheme
    val showDialog = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    LaunchedEffect(state.isGameOver) {
        if (state.isGameOver) {
            showDialog.value = true
        }
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(text = "Game Over")
            },
            text = {
                Text(
                    text = if (viewModel.checkForWinner(state.board)) {
                        if (state.currentPlayer == Player.PLAYER_1) {
                            "You win!"
                        } else "Your opponent wins!"
                    } else {
                        "It's a draw!"
                    }
                )
            },
            confirmButton = {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    onClick = {
                        showDialog.value = false
                        viewModel.resetGame()
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 10.dp),
                        text = "Reset Game",
                        textAlign = TextAlign.Center,
                        style = typo.bodyLarge.copy(
                            color = Color.White
                        )
                    )
                }
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White,
            ),
            title = {
                Text(
                    state.gameMode.name.lowercase(Locale.ROOT).replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .height(45.dp)
                        .width(45.dp)
                        .background(
                            color = Color.Transparent,
                        ),
                    onClick = { navController.navigate(route = Screen.Initial.route) },
                ) {
                    Icon(
                        Icons.Rounded.Home,
                        contentDescription = "Home",
                        tint = Color.White,
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            },
            scrollBehavior = scrollBehavior,
        )
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            PlayerMarker(
                state = PlayerMakerState(
                    PlayerMarkerType.X,
                    isSelected = state.currentPlayer == Player.PLAYER_1,
                    itemsCount = state.player1MoveCount
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            PlayerMarker(
                state = PlayerMakerState(
                    PlayerMarkerType.O,
                    isSelected = state.currentPlayer == Player.PLAYER_2,
                    itemsCount = state.player2MoveCount
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BoardContent(
            onItemSelected,
            boardState = state.board,
            isGameOver = state.isGameOver,
            nextMoveToRemove = state.nextMoveToRemove
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
                text = "Reset Game",
                onClick = viewModel::resetGame,
                isEnable = viewModel.canResetGame()
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomButton(
                text = "Rules",
                onClick = { navController.navigate(Screen.Rules.route) },
            )
        }
    }
}

@Composable
fun BoardContent(
    onItemSelected: (Int, Int) -> Unit,
    boardState: List<List<Player?>>,
    isGameOver: Boolean = false,
    nextMoveToRemove: Pair<Int, Int>? = null
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
                isGameOver = isGameOver,
                nextMoveToRemove = nextMoveToRemove
            )
        }
    }
}

@Composable
fun BoardRow(
    onItemSelected: (Pair<Int, Int>) -> Unit,
    positions: List<Pair<Int, Int>> = emptyList(),
    rowState: List<Player?>,
    isGameOver: Boolean,
    nextMoveToRemove: Pair<Int, Int>? = null
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
                isClickable = !isGameOver,
                isNextToRemove = nextMoveToRemove == pair
            )
        }
    }
}

@Composable
fun BoardKeyBox(
    onItemSelected: () -> Unit,
    player: Player? = null,
    isClickable: Boolean = true,
    isNextToRemove: Boolean = false
) {
    val color = MaterialTheme.colorScheme
    val alpha = if (isNextToRemove) 0.5f else 1f
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(80.dp)
            .background(
                color = color.secondary.copy(alpha = alpha), shape = RoundedCornerShape(
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