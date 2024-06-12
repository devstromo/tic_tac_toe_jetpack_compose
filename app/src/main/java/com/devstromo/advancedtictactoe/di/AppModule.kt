package com.devstromo.advancedtictactoe.di

import com.devstromo.advancedtictactoe.data.online.bluetooth.AndroidBluetoothController
import com.devstromo.advancedtictactoe.domain.online.bluetooth.BluetoothController
import com.devstromo.advancedtictactoe.presentation.GameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<BluetoothController> { AndroidBluetoothController(get()) }
    viewModel { GameViewModel(get()) }
}