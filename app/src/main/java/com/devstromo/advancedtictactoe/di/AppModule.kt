package com.devstromo.advancedtictactoe.di

import com.devstromo.advancedtictactoe.presentation.main.GameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { GameViewModel() }
}