package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.GameViewModel

@Composable
fun MainScreenMenu(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GameViewModel,
) {
    val currentContext = LocalContext.current
    val menuItems = arrayOf(
        MenuItem(
            title = currentContext.getString(R.string.mode_classic),
            onClick = {
                viewModel.resetIcons()
                navController.navigate(route = Screen.Game.createRoute(GameMode.CLASSIC))
            },
            isActive = true
        )
    )
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        menuItems
            .filter { item -> item.isActive }
            .forEach {
                Spacer(modifier = Modifier.weight(1f))
                MenuItemView(menuItem = it)
            }
    }
}

@Composable
internal fun MenuItemView(
    menuItem: MenuItem,
) {
    CustomButton(
        text = menuItem.title,
        onClick = menuItem.onClick,
    )
}

internal data class MenuItem(
    val title: String,
    val onClick: () -> Unit,
    val isActive: Boolean = false,
)