package com.qlmat.android.onelab_project.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import com.qlmat.android.onelab_project.presentation.MainActivity
import com.qlmat.android.onelab_project.utils.createNotification
import com.qlmat.android.onelab_project.utils.getPendingIntent
import java.util.UUID
import javax.inject.Inject

class CustomNotificationManager @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(notification: CustomNotification) {
        val pendingIntent = context.getPendingIntent(MainActivity::class.java)
        buildNotificationChannel(notification)
        val myNotification = context.createNotification(
            notification = notification,
            pendingIntent = pendingIntent
        )
//        notificationManager.notify(
//            System.currentTimeMillis().toInt(),
//            notification
//        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildNotificationChannel(notification: CustomNotification) {
        val chan = NotificationChannel(
            notification.channelId,
            context.getString(notification.channelName),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        chan.lightColor = Color.RED
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        if (notificationManager.notificationChannels.all { it.id != notification.channelId }) {
            notificationManager.createNotificationChannel(chan)
        }
    }
}

data class CustomNotification(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val text: String,
    val channelId: String = UUID.randomUUID().toString(),
    @StringRes val channelName: Int,
    @DrawableRes val icon: Int,
    @StringRes val channelDescription: Int
)