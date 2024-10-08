package com.devstromo.advancedtictactoe

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.rive.runtime.kotlin.core.Rive
import com.devstromo.advancedtictactoe.config.LocalAppLanguage
import com.devstromo.advancedtictactoe.config.helpers.setLocale
import com.devstromo.advancedtictactoe.domain.GameMode
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.navigation.createRuleRoute
import com.devstromo.advancedtictactoe.presentation.infos.GameModeInfoScreen
import com.devstromo.advancedtictactoe.presentation.infos.GameModesInfoScreen
import com.devstromo.advancedtictactoe.presentation.infos.HelpScreen
import com.devstromo.advancedtictactoe.presentation.infos.rules.AIChallengeModeInfoScreen
import com.devstromo.advancedtictactoe.presentation.infos.rules.AdvanceModeInfoScreen
import com.devstromo.advancedtictactoe.presentation.infos.rules.ClassicModeInfoScreen
import com.devstromo.advancedtictactoe.presentation.initial.InitialScreen
import com.devstromo.advancedtictactoe.presentation.main.GameScreen
import com.devstromo.advancedtictactoe.presentation.main.GameUiState
import com.devstromo.advancedtictactoe.presentation.main.GameViewModel
import com.devstromo.advancedtictactoe.ui.theme.AdvancedTicTacToeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModel()
    private val currentLanguage = mutableStateOf("en")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeApp()
        setContent { RenderUI() }
    }

    private fun initializeApp() {
        Rive.init(this)
        createNotificationChannel()
        loadAppLanguage()
    }

    private fun loadAppLanguage() {
        val prefs: SharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = prefs.getString("app_language", "en") ?: "en"
        setLocale(this, language)
        currentLanguage.value = language
    }

    @Composable
    private fun RenderUI() {
        AdvancedTicTacToeTheme(
            darkTheme = true,
            dynamicColor = false
        ) {
            val state by viewModel.uiState.collectAsState()
            val navController = rememberNavController()

            CompositionLocalProvider(LocalAppLanguage provides currentLanguage) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavigation(navController, state)
                }
            }
        }
    }

    @Composable
    private fun SetupNavigation(navController: NavHostController, state: GameUiState) {
        NavHost(
            navController = navController,
            startDestination = Screen.Initial.route
        ) {
            composable(Screen.Initial.route) {
                viewModel.resetGame()
                InitialScreen(navController = navController, viewModel = viewModel)
            }
            composable(
                route = Screen.Game.route,
                arguments = listOf(navArgument("gameMode") { type = NavType.StringType })
            ) { backStackEntry ->
                val gameMode = backStackEntry.arguments?.getString("gameMode")?.let {
                    GameMode.valueOf(it)
                } ?: GameMode.CLASSIC
                viewModel.updateGameMode(gameMode)
                GameScreen(
                    navController = navController,
                    viewModel = viewModel,
                    state = state,
                    ruleRoute = createRuleRoute(gameMode)
                )
            }
            composable(Screen.Help.route) {
                HelpScreen(navController = navController)
            }
            composable(Screen.GameModesInfo.route) {
                GameModesInfoScreen(navController = navController)
            }
            composable(
                route = Screen.GameModeInfo.route,
                arguments = listOf(
                    navArgument("titleId") { type = NavType.IntType },
                    navArgument("bodyId") { type = NavType.IntType })
            ) { backStackEntry ->
                val titleId = backStackEntry.arguments?.getInt("titleId")
                val bodyId = backStackEntry.arguments?.getInt("bodyId")
                GameModeInfoScreen(
                    navController = navController,
                    titleId = titleId,
                    bodyId = bodyId
                )
            }
            composable(Screen.GameModeInfoClassical.route) {
                ClassicModeInfoScreen(popBackStack = { navController.popBackStack() })
            }
            composable(Screen.GameModeInfoAdvance.route) {
                AdvanceModeInfoScreen(popBackStack = { navController.popBackStack() })
            }
            composable(Screen.GameModeInfoAIChallenge.route) {
                AIChallengeModeInfoScreen(popBackStack = { navController.popBackStack() })
            }
        }
    }

    private fun createNotificationChannel() {
        val name = "Daily Reminder"
        val descriptionText = "Reminder to play Tic Tac Toe"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("daily_reminder_channel", name, importance).apply {
            description = descriptionText
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}