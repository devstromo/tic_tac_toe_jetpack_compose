package com.devstromo.advancedtictactoe.presentation

import com.devstromo.advancedtictactoe.domain.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GameViewModelTest {
    @Test
    fun testCheckForWinnerShouldWinWithNoWin() {
        val viewModel = GameViewModel()
        val board = List(3) { List(3) { Player.NONE } }
        viewModel.updateStateForTesting(board)
        assertFalse(viewModel.checkForWinner(board))
    }

    @Test
    fun testCheckForWinnerShouldWinWithRowWin() {
        val viewModel = GameViewModel()
        val board = listOf(
            listOf(Player.PLAYER_1, Player.PLAYER_1, Player.PLAYER_1),
            listOf(Player.NONE, Player.NONE, Player.NONE),
            listOf(Player.NONE, Player.NONE, Player.NONE)
        )
        viewModel.updateStateForTesting(
            board
        )
        assertTrue(viewModel.checkForWinner(board))
    }

    @Test
    fun testCheckForWinnerShouldWinWithColumnWin() {
        val viewModel = GameViewModel()
        val board =  listOf(
            listOf(Player.PLAYER_1, Player.NONE, Player.NONE),
            listOf(Player.PLAYER_1, Player.NONE, Player.NONE),
            listOf(Player.PLAYER_1, Player.NONE, Player.NONE)
        )
        viewModel.updateStateForTesting(
            board
        )
        assertTrue(viewModel.checkForWinner(board))
    }

    @Test
    fun testCheckForWinnerShouldWinWithDiagonal() {
        val viewModel = GameViewModel()
        val board = listOf(
            listOf(Player.PLAYER_1, Player.NONE, Player.NONE),
            listOf(Player.NONE, Player.PLAYER_1, Player.NONE),
            listOf(Player.NONE, Player.NONE, Player.PLAYER_1)
        )
        viewModel.updateStateForTesting(
            board
        )
        assertTrue(viewModel.checkForWinner(board))
    }

    @Test
    fun testCheckIfCanResetShouldNotReset() {
        val viewModel = GameViewModel()
        viewModel.updateStateForTesting(
            listOf(
                listOf(Player.PLAYER_1, Player.NONE, Player.NONE),
                listOf(Player.NONE, Player.NONE, Player.NONE),
                listOf(Player.NONE, Player.NONE, Player.NONE)
            )
        )
        assertTrue(viewModel.canResetGame())
    }

    @Test
    fun testCheckIfCanResetShouldReset() {
        val viewModel = GameViewModel()
        viewModel.updateStateForTesting(
            listOf(
                listOf(Player.PLAYER_1, Player.NONE, Player.NONE),
                listOf(Player.NONE, Player.NONE, Player.NONE),
                listOf(Player.NONE, Player.NONE, Player.NONE)
            )
        )
        assertTrue(viewModel.canResetGame())
    }

    @Test
    fun testResetShouldResetTheBoard() {
        val viewModel = GameViewModel()
        viewModel.updateStateForTesting(
            listOf(
                listOf(Player.PLAYER_1, Player.NONE, Player.NONE),
                listOf(Player.NONE, Player.NONE, Player.NONE),
                listOf(Player.NONE, Player.NONE, Player.NONE)
            )
        )
        assertTrue(viewModel.canResetGame())
        viewModel.resetGame()
        assertFalse(viewModel.canResetGame())
    }
}