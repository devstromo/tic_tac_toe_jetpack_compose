package com.devstromo.advancedtictactoe.presentation.components

import androidx.compose.runtime.Composable
import com.devstromo.advancedtictactoe.data.item.MenuItem

@Composable
fun MenuItemView(
    menuItem: MenuItem,
) {
    CustomButton(
        text = menuItem.title,
        onClick = menuItem.onClick,
    )
}
