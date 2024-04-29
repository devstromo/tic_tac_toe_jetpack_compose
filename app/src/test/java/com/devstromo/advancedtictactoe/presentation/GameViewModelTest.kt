package com.devstromo.advancedtictactoe.presentation

import com.devstromo.advancedtictactoe.domain.CellState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GameViewModelTest {
    @Test
    fun testCheckForWinner_noWinner() {
        val viewModel = GameViewModel()
        viewModel.updateStateForTesting(Array(3) { Array(3) { CellState.CLEAR } })
        assertFalse(viewModel.checkForWinner())
    }

    @Test
    fun testCheckForWinner_rowWin() {
        val viewModel = GameViewModel()
        viewModel.updateStateForTesting(arrayOf(
            arrayOf(CellState.MARK_X, CellState.MARK_X, CellState.MARK_X),
            arrayOf(CellState.CLEAR, CellState.CLEAR, CellState.CLEAR),
            arrayOf(CellState.CLEAR, CellState.CLEAR, CellState.CLEAR)
        ))
        assertTrue(viewModel.checkForWinner())
    }

    @Test
    fun testCheckForWinner_columnWin() {
        val viewModel = GameViewModel()
        viewModel.updateStateForTesting(arrayOf(
            arrayOf(CellState.MARK_X, CellState.CLEAR, CellState.CLEAR),
            arrayOf(CellState.MARK_X, CellState.CLEAR, CellState.CLEAR),
            arrayOf(CellState.MARK_X, CellState.CLEAR, CellState.CLEAR)
        ))
        assertTrue(viewModel.checkForWinner())
    }

    @Test
    fun testCheckForWinner_diagonalWin() {
        val viewModel = GameViewModel()
        viewModel.updateStateForTesting(arrayOf(
            arrayOf(CellState.MARK_X, CellState.CLEAR, CellState.CLEAR),
            arrayOf(CellState.CLEAR, CellState.MARK_X, CellState.CLEAR),
            arrayOf(CellState.CLEAR, CellState.CLEAR, CellState.MARK_X)
        ))
        assertTrue(viewModel.checkForWinner())
    }

}