package com.devstromo.advancedtictactoe.domain.notifaction

import com.devstromo.advancedtictactoe.data.notification.NotificationRepository

class NotificationUseCase(private val notificationRepository: NotificationRepository) {
    fun scheduleDailyReminder() {
        notificationRepository.scheduleDailyReminder()
    }

    fun cancelReminder() {
        notificationRepository.cancelReminder()
    }
}