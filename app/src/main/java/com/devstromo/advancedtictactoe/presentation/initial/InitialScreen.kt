package com.devstromo.advancedtictactoe.presentation.initial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.components.CustomButton

@Composable
fun InitialScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 45.dp,
            ),
        verticalArrangement = Arrangement.Center
    ) {
        CustomButton(
            text = "Play",
            onClick = {
                navController.navigate(route = Screen.Game.route)
            },
        )
    }
}