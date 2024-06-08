package com.devstromo.advancedtictactoe.presentation.bluetooth

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
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import com.devstromo.advancedtictactoe.presentation.components.CustomButton

@Composable
fun BluetoothGameScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel,
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
        CustomButton(
            text = stringResource(R.string.bluetooth_game_create_title),
            onClick = {

            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(
            text = stringResource(R.string.bluetooth_game_join_title),
            onClick = {

            },
        )
    }
}