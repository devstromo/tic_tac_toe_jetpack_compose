package com.devstromo.advancedtictactoe.presentation.initial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.rive.runtime.kotlin.core.ExperimentalAssetLoader
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.config.helpers.RiveAnimation
import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import com.devstromo.advancedtictactoe.presentation.components.CustomButton

@OptIn(ExperimentalAssetLoader::class)
@Composable
fun InitialScreen(
    navController: NavController,
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 45.dp,
                vertical = 20.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        RiveAnimation(
            resId = R.raw.tic_tac_toe_title,
            autoplay = true,
            animationName = "Timeline 1",
            contentDescription = "Some content Description"
        )
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            text = stringResource(R.string.mode_classic),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Game.createRoute(GameMode.CLASSIC))
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(
            text = stringResource(R.string.game_mode_advance_title),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Game.createRoute(GameMode.ADVANCED))
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(
            text = stringResource(R.string.game_mode_online_title),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Bluetooth.route)
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(
            text = stringResource(R.string.mode_against_bot),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Game.createRoute(GameMode.BOT))
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(
            text = "Rules",
            onClick = {
                navController.navigate(route = Screen.Rules.route)
            },
        )
    }
}