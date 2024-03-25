package com.qlmat.android.onelab_project

import android.app.Application
import com.qlmat.android.onelab_project.core.worker.CacheWorker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppInit: Application() {
    override fun onCreate() {
        super.onCreate()
        CacheWorker.enqueueWorker(this)
    }
}