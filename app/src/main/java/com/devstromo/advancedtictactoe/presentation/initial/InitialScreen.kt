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
import androidx.compose.ui.platform.LocalContext
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
import com.devstromo.advancedtictactoe.presentation.components.MainScreenMenu

@OptIn(ExperimentalAssetLoader::class)
@Composable
fun InitialScreen(
    navController: NavController,
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val currentContext = LocalContext.current
    val menuItems = listOf(
        MenuItem(
            title = currentContext.getString(R.string.mode_classic),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Game.createRoute(GameMode.CLASSIC))
            },
            isActive = true
        ),
        MenuItem(
            title = currentContext.getString(R.string.game_mode_advance_title),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Game.createRoute(GameMode.ADVANCED))
            },
            isActive = true
        ),
        MenuItem(
            title = currentContext.getString(R.string.game_mode_online_title),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Bluetooth.route)
            },
            isActive = false
        ),
        MenuItem(
            title = currentContext.getString(R.string.mode_against_bot),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Game.createRoute(GameMode.BOT))
            },
            isActive = true
        ),
        MenuItem(
            title = "Rules",
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Rules.route)
            },
            isActive = true
        ),
    )

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
        MainScreenMenu(
            menuItems = menuItems
        )
    }
}

data class MenuItem(
    val title: String,
    val onClick: () -> Unit,
    val isActive: Boolean = false,
)