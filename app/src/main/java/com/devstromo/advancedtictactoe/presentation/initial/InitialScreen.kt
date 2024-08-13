package com.devstromo.advancedtictactoe.presentation.initial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.config.LocalAppLanguage
import com.devstromo.advancedtictactoe.config.helpers.RiveAnimation
import com.devstromo.advancedtictactoe.config.helpers.setLocale
import com.devstromo.advancedtictactoe.data.item.MenuItem
import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import com.devstromo.advancedtictactoe.presentation.components.ConfigButton
import com.devstromo.advancedtictactoe.presentation.components.MenuItemsList
import com.devstromo.advancedtictactoe.presentation.components.dialog.LanguageDialog

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
    )

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        LanguageDialog(
            buttonAction = { newLanguage ->
                if (newLanguage.isNotEmpty()) {
                    setLocale(currentContext, newLanguage)
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
                text = "?",
                onClick = { navController.navigate(route = Screen.Help.route) }
            )
            ConfigButton(
                text = if (currentLanguage.value == "en") "EN" else "ES",
                onClick = { showDialog.value = true }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val boardSize = maxHeight * 0.5f
            Box(
                modifier = Modifier
                    .size(boardSize)
            ) {
                RiveAnimation(
                    resId = R.raw.tic_tac_toe_title,
                    autoplay = true,
                    animationName = "Timeline 1",
                    contentDescription = "Some content Description"
                )
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        MenuItemsList(
            menuItems = menuItems
        )
    }
}