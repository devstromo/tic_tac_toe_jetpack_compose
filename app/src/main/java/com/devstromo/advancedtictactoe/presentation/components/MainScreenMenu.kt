package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.presentation.initial.MenuItem

@Composable
fun MainScreenMenu(
    modifier: Modifier = Modifier,
    menuItems: List<MenuItem>
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
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

@Composable
internal fun MenuItemView(
    menuItem: MenuItem,
) {
    CustomButton(
        text = menuItem.title,
        onClick = menuItem.onClick,
    )
}

