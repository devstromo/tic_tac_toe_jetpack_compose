package com.devstromo.advancedtictactoe.data

import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.domain.GameMode


object PlayerIconsGenerator {
    private val animalsIcons = listOf(
        R.drawable.ic_alpaca,
        R.drawable.ic_bat,
        R.drawable.ic_butterfly,
        R.drawable.ic_crocodile,
        R.drawable.ic_crow,
        R.drawable.ic_deer,
    )

    private var player1Icon: Int = -1
    private var player2Icon: Int = -1

    fun generatePlayer1Icon(): Int {
        return if (player1Icon != -1)
            player1Icon
        else {
            player1Icon = animalsIcons.random()
            player1Icon
        }
    }

    fun generatePlayer2Icon(gameMode: GameMode): Int {
        return if (player2Icon != -1)
            player2Icon
        else {
            if (gameMode == GameMode.BOT)
                R.drawable.ic_robot
            else {
                player2Icon = animalsIcons
                    .filter { id -> id != player1Icon }
                    .random()
                player2Icon
            }
        }
    }

    fun clearIcons() {
        player1Icon = -1;
        player2Icon = -1;
    }

}



