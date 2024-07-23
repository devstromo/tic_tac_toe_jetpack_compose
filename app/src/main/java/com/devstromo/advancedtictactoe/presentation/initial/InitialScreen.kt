package com.devstromo.advancedtictactoe.presentation.initial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.rive.runtime.kotlin.core.ExperimentalAssetLoader
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.config.LocalAppLanguage
import com.devstromo.advancedtictactoe.config.helpers.RiveAnimation
import com.devstromo.advancedtictactoe.config.helpers.setLocale
import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import com.devstromo.advancedtictactoe.presentation.components.ConfigButton
import com.devstromo.advancedtictactoe.presentation.components.MainScreenMenu
import com.devstromo.advancedtictactoe.presentation.components.dialog.AnimatedDialog

@OptIn(ExperimentalAssetLoader::class)
@Composable
fun InitialScreen(
    navController: NavController,
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val currentContext = LocalContext.current
    val currentLanguage = LocalAppLanguage.current
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
            title = currentContext.getString(R.string.title_rules),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Rules.route)
            },
            isActive = true
        ),
    )

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        AnimatedDialog(
            buttonAction = { newLanguage ->
                if (newLanguage.isNotEmpty()) {
                    // Change app language
                    setLocale(currentContext, newLanguage)
                    // Update the CompositionLocal value
                    currentLanguage.value = newLanguage
                }
            },
            onDismissRequest = { showDialog.value = false }
        )
    }
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ConfigButton(
                text = "¡",
                onClick = { /*TODO*/ }
            )
            ConfigButton(
                text = if (currentLanguage.value == "en") "EN" else "ES",
                onClick = { showDialog.value = true }
            )
        }
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