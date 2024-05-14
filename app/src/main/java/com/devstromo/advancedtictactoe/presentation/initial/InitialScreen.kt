package com.devstromo.advancedtictactoe.presentation.initial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.components.CustomButton

@Composable
fun InitialScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val typo = MaterialTheme.typography
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
        Text(
            text = "Tic-Tac-Toe",
            style = typo.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            text = "Play",
            onClick = {
                navController.navigate(route = Screen.Game.route)
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(
            text = "Rules",
            onClick = {
                navController.navigate(route = Screen.Game.route)
            },
            isEnable = false
        )
    }
}