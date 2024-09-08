package com.devstromo.advancedtictactoe.data.notification

interface NotificationRepository {
    fun scheduleDailyReminder()
    fun cancelReminder()
}