package com.devstromo.advancedtictactoe.di

import com.devstromo.advancedtictactoe.data.notification.NotificationRepository
import com.devstromo.advancedtictactoe.data.notification.NotificationRepositoryImpl
import com.devstromo.advancedtictactoe.domain.notifaction.NotificationUseCase
import com.devstromo.advancedtictactoe.presentation.main.GameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { GameViewModel() }
}

val notificationModule = module {
    single<NotificationRepository> { NotificationRepositoryImpl(get()) }
    factory { NotificationUseCase(get()) }
}