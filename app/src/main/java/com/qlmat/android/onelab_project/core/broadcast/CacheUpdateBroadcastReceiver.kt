package com.qlmat.android.onelab_project.core.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

const val ACTION_CACHE = "com.qlmat.android.onelab_project.action.ACTION_CACHE_UPDATED"

class CacheUpdateBroadcastReceiver : BroadcastReceiver() {

    private lateinit var channelId: String

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_CACHE) {
            if (context != null) {
                showNotification(context)
            }
        }
    }

    private fun showNotification(context: Context) {

    }
}