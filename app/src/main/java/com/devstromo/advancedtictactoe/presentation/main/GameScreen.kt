package com.devstromo.advancedtictactoe.presentation.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.data.game.BoardRowConfigSize
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
    ruleRoute: String,
) {
    val typo = MaterialTheme.typography
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val showBackDialog = remember { mutableStateOf(false) }

    LaunchedEffect(state.isGameOver) {
        if (state.isGameOver) {
            showDialog.value = true
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(text = stringResource(R.string.game_over))
            },
            text = {
                Text(
                    text = if (viewModel.checkForWinner(state.board)) {
                        if (state.currentPlayer == Player.PLAYER_1) {
                            stringResource(R.string.player_one_win)
                        } else stringResource(R.string.player_two_wins)
                    } else {
                        stringResource(R.string.draw_string)
                    }
                )
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
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
                        text = stringResource(id = R.string.reset_game),
                        textAlign = TextAlign.Center,
                        style = typo.bodyLarge.copy(color = Color.White)
                    )
                }
            }
        )
    }

    if (showBackDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(text = stringResource(R.string.leave_game_exit))
            },
            text = {
                Text(
                    text = stringResource(R.string.leave_game)
                )
            },
            dismissButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    onClick = {
                        showBackDialog.value = false
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 10.dp),
                        text = stringResource(id = R.string.title_cancel),
                        textAlign = TextAlign.Center,
                        style = typo.bodyLarge.copy(color = Color.White)
                    )
                }
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    onClick = {
                        showBackDialog.value = false
                        navController.navigate(route = Screen.Initial.route)
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 10.dp),
                        text = stringResource(id = R.string.title_accept),
                        textAlign = TextAlign.Center,
                        style = typo.bodyLarge.copy(color = Color.White)
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 30.dp
            ),
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
                        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(
                    modifier = Modifier
                        .height(45.dp)
                        .width(45.dp)
                        .background(
                            color = Color.Transparent,
                        ),
                    onClick = {
                        showBackDialog.value = true
                    },
                ) {
                    Icon(
                        Icons.Rounded.Home,
                        contentDescription = "Home",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
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
                ),
                playerIconId = viewModel.player1Icon()
            )
            Spacer(modifier = Modifier.width(20.dp))
            PlayerMarker(
                state = PlayerMakerState(
                    PlayerMarkerType.O,
                    isSelected = state.currentPlayer == Player.PLAYER_2,
                    itemsCount = state.player2MoveCount
                ),
                playerIconId = viewModel.player2Icon()
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BoardContent(
            boardState = state.board,
            isGameOver = state.isGameOver,
            nextMoveToRemove = state.nextMoveToRemove,
            playSound = { soundId ->
                viewModel.playSound(context, soundId)
            },
            onItemSelected = viewModel::onItemSelected
        )
        Spacer(modifier = Modifier.weight(1f))
        FooterButtons(viewModel, navController, ruleRoute)
    }
}

@Composable
private fun FooterButtons(
    viewModel: GameViewModel,
    navController: NavController,
    ruleRoute: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                start = 25.dp,
                end = 25.dp
            )
    ) {
        CustomButton(
            text = stringResource(R.string.reset_game),
            onClick = viewModel::resetGame,
            isEnable = viewModel.canResetGame()
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(
            text = stringResource(id = R.string.title_game_rules),
            onClick = { navController.navigate(ruleRoute) },
        )
    }
}

@Composable
fun BoardContent(
    boardState: List<List<Player?>>,
    isGameOver: Boolean = false,
    nextMoveToRemove: Pair<Int, Int>? = null,
    playSound: (Int) -> Unit,
    onItemSelected: (Int, Int) -> Unit
) {
    val soundResources = listOf(
        R.raw.sound_1, R.raw.sound_2, R.raw.sound_3,
        R.raw.sound_4, R.raw.sound_5, R.raw.sound_6,
        R.raw.sound_7, R.raw.sound_8, R.raw.sound_9
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val boardSize = maxHeight * 0.7f
        val keySize = (boardSize - 30.dp) / 3

        Column(
            modifier = Modifier
                .size(boardSize)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            for (row in 0..2) {
                val positions = List(3) { col -> Pair(row, col) }
                BoardRow(
                    onItemSelected = { selectedPair ->
                        Log.i("Board", "Pair $selectedPair")
                        onItemSelected(selectedPair.first, selectedPair.second)
                    },
                    positions = positions,
                    rowState = boardState[row],
                    isGameOver = isGameOver,
                    nextMoveToRemove = nextMoveToRemove,
                    soundIndex = { index -> playSound(soundResources[index]) },
                    boardRowConfigSize = BoardRowConfigSize(
                        keySize = keySize,
                        topMargin = if (row == 0) 10.dp else 3.dp,
                        bottomMargin = if (row == 2) 10.dp else 3.dp
                    )
                )
            }
        }
    }
}

@Composable
fun BoardRow(
    onItemSelected: (Pair<Int, Int>) -> Unit,
    positions: List<Pair<Int, Int>> = emptyList(),
    rowState: List<Player?>,
    isGameOver: Boolean,
    nextMoveToRemove: Pair<Int, Int>? = null,
    soundIndex: (Int) -> Unit,
    boardRowConfigSize: BoardRowConfigSize
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = boardRowConfigSize.topMargin,
                bottom = boardRowConfigSize.bottomMargin,
                start = 10.dp,
                end = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        positions.forEachIndexed { index, pair ->
            BoardKeyBox(
                onItemSelected = { onItemSelected(pair) },
                player = rowState[pair.second],
                isClickable = !isGameOver,
                isNextToRemove = nextMoveToRemove == pair,
                keySize = boardRowConfigSize.keySize,
                playSound = {
                    soundIndex(index)
                }
            )
            if (index < positions.size - 1) {
                Spacer(modifier = Modifier.width(5.dp)) // Space between key boxes
            }
        }
    }
}

@Composable
fun BoardKeyBox(
    onItemSelected: () -> Unit,
    player: Player? = null,
    isClickable: Boolean = true,
    isNextToRemove: Boolean = false,
    keySize: Dp,
    playSound: () -> Unit,
) {
    val color = MaterialTheme.colorScheme
    val alpha = if (isNextToRemove) 0.5f else 1f
    Box(
        modifier = Modifier
            .size(keySize)
            .background(
                color = color.secondary,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                enabled = isClickable,
                onClick = {
                    onItemSelected()
                    playSound()
                },
                indication = rememberRipple(
                    bounded = true,
                    radius = keySize / 2,
                    color = color.primary
                ),
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(
                top = keySize * .18f,
                start = keySize * .08f,
            ),
            text = when (player) {
                Player.PLAYER_1 -> "X"
                Player.PLAYER_2 -> "O"
                else -> ""
            },
            style = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
            color = when (player) {
                Player.PLAYER_1 -> kPlayerXMarkColor.copy(alpha = alpha)
                Player.PLAYER_2 -> kPlayerOMarkColor.copy(alpha = alpha)
                else -> Color.Transparent
            }
        )
    }
}