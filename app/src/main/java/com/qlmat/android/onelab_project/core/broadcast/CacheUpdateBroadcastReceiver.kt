package com.qlmat.android.onelab_project.core.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.qlmat.android.onelab_project.R
import com.qlmat.android.onelab_project.core.notification.CustomNotification
import com.qlmat.android.onelab_project.core.notification.CustomNotificationManager
import javax.inject.Inject

const val ACTION_CACHE = "com.qlmat.android.onelab_project.action.ACTION_CACHE_UPDATED"

class CacheUpdateBroadcastReceiver @Inject constructor(
    private val notificationManager: CustomNotificationManager
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_CACHE) {
            if (context != null) {
                showNotification()
            }
        }
    }

    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = CustomNotification(
                title = "Cache Updated",
                text = "Your cache has been updated",
                channelName = R.string.notification_channel_name,
                icon = R.drawable.ic_notification,
                channelDescription = R.string.notification_channel_description
            )
            notificationManager.showNotification(notification)
        }
    }

}