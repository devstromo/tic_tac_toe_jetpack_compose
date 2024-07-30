package com.devstromo.advancedtictactoe.presentation.help

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.data.item.MenuItem
import com.devstromo.advancedtictactoe.presentation.components.MenuItemView

@Composable
fun HelpScreen(
    modifier: Modifier = Modifier
) {
    val currentContext = LocalContext.current
    val menuItems = listOf(
        MenuItem(
            title = currentContext.getString(R.string.game_modes_title),
            onClick = { },
            isActive = true
        ),
        MenuItem(
            title = currentContext.getString(R.string.title_rules),
            onClick = { },
            isActive = true
        ),
    )
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        menuItems
            .filter { item -> item.isActive }
            .forEach {
                Spacer(modifier = Modifier.height(10.dp))
                MenuItemView(menuItem = it)
            }
    }
}