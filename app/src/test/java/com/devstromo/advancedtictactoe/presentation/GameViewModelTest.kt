package com.devstromo.advancedtictactoe.presentation

import com.devstromo.advancedtictactoe.domain.Player
import com.devstromo.advancedtictactoe.domain.online.bluetooth.BluetoothController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GameViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testScope: TestScope

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testScope = TestScope(testDispatcher)
    }

    @Test
    fun testCheckForWinnerShouldWinWithNoWin() = runTest {
        val viewModel = GameViewModel(testDispatcher)
        val board = List(3) { MutableList(3) { Player.NONE } }
        viewModel.updateStateForTesting(board)
        assertFalse(viewModel.checkForWinner(board))
    }

    @Test
    fun testCheckForWinnerShouldWinWithRowWin() = runTest {
        val viewModel = GameViewModel(testDispatcher)
        val board = listOf(
            mutableListOf(Player.PLAYER_1, Player.PLAYER_1, Player.PLAYER_1),
            mutableListOf(Player.NONE, Player.NONE, Player.NONE),
            mutableListOf(Player.NONE, Player.NONE, Player.NONE)
        )
        viewModel.updateStateForTesting(board)
        assertTrue(viewModel.checkForWinner(board))
    }

    @Test
    fun testCheckForWinnerShouldWinWithColumnWin() = runTest {
        val viewModel = GameViewModel(testDispatcher)
        val board = listOf(
            mutableListOf(Player.PLAYER_1, Player.NONE, Player.NONE),
            mutableListOf(Player.PLAYER_1, Player.NONE, Player.NONE),
            mutableListOf(Player.PLAYER_1, Player.NONE, Player.NONE)
        )
        viewModel.updateStateForTesting(board)
        assertTrue(viewModel.checkForWinner(board))
    }

    @Test
    fun testCheckForWinnerShouldWinWithDiagonal() = runTest {
        val viewModel = GameViewModel(testDispatcher)
        val board = listOf(
            mutableListOf(Player.PLAYER_1, Player.NONE, Player.NONE),
            mutableListOf(Player.NONE, Player.PLAYER_1, Player.NONE),
            mutableListOf(Player.NONE, Player.NONE, Player.PLAYER_1)
        )
        viewModel.updateStateForTesting(board)
        assertTrue(viewModel.checkForWinner(board))
    }

    @Test
    fun testCheckIfCanResetShouldNotReset() = runTest {
        val viewModel = GameViewModel(testDispatcher)
        viewModel.updateStateForTesting(
            listOf(
                mutableListOf(Player.PLAYER_1, Player.NONE, Player.NONE),
                mutableListOf(Player.NONE, Player.NONE, Player.NONE),
                mutableListOf(Player.NONE, Player.NONE, Player.NONE)
            )
        )
        assertTrue(viewModel.canResetGame())
    }

    @Test
    fun testCheckIfCanResetShouldReset() = runTest {
        val viewModel = GameViewModel(testDispatcher)
        viewModel.updateStateForTesting(
            listOf(
                mutableListOf(Player.PLAYER_1, Player.NONE, Player.NONE),
                mutableListOf(Player.NONE, Player.NONE, Player.NONE),
                mutableListOf(Player.NONE, Player.NONE, Player.NONE)
            )
        )
        assertTrue(viewModel.canResetGame())
    }

    @Test
    fun testResetShouldResetTheBoard() = runTest {
        val viewModel = GameViewModel(testDispatcher)
        viewModel.updateStateForTesting(
            listOf(
                mutableListOf(Player.PLAYER_1, Player.NONE, Player.NONE),
                mutableListOf(Player.NONE, Player.NONE, Player.NONE),
                mutableListOf(Player.NONE, Player.NONE, Player.NONE)
            )
        )
        assertTrue(viewModel.canResetGame())
        viewModel.resetGame()
        assertFalse(viewModel.canResetGame())
    }
}