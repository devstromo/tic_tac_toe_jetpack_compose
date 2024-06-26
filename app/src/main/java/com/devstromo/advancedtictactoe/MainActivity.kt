package com.devstromo.advancedtictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.rive.runtime.kotlin.core.Rive
import com.devstromo.advancedtictactoe.di.appModule
import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.GameScreen
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import com.devstromo.advancedtictactoe.presentation.bluetooth.BluetoothGameScreen
import com.devstromo.advancedtictactoe.presentation.components.QRCodeScreen
import com.devstromo.advancedtictactoe.presentation.initial.InitialScreen
import com.devstromo.advancedtictactoe.presentation.rules.RulesScreen
import com.devstromo.advancedtictactoe.ui.theme.AdvancedTicTacToeTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Rive.init(this)
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
        setContent {

            AdvancedTicTacToeTheme(
                darkTheme = true,
                dynamicColor = false
            ) {
                val state by viewModel.uiState.collectAsState()
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Initial.route
                    ) {
                        composable(route = Screen.Initial.route) {
                            InitialScreen(
                                navController = navController,
                                viewModel = viewModel,
                            )
                        }
                        composable(
                            route = Screen.Game.route,
                            arguments = listOf(navArgument("gameMode") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val gameMode = backStackEntry.arguments?.getString("gameMode")?.let {
                                GameMode.valueOf(it)
                            } ?: GameMode.CLASSIC
                            viewModel.updateGameMode(gameMode)
                            GameScreen(
                                navController = navController,
                                viewModel = viewModel,
                                state = state,
                            )
                        }
                        composable(route = Screen.Rules.route) {
                            RulesScreen(navController = navController)
                        }
                        composable(route = Screen.Bluetooth.route) {
                            BluetoothGameScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                        composable(route = Screen.QRScanner.route) {
                            QRCodeScreen(
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}