package com.qlmat.android.onelab_project.core.worker

import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.qlmat.android.onelab_project.core.broadcast.ACTION_CACHE
import com.qlmat.android.onelab_project.domain.PopularMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CacheWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val repository: PopularMoviesRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.fetchPopularMovies(needToRefresh = false)
            } catch (ex: Exception) {
                Result.failure()
            }
        }
        val intent = Intent(ACTION_CACHE)
        applicationContext.sendBroadcast(intent)
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "CacheWorker"

        fun enqueueWorker(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(networkType = NetworkType.CONNECTED)
                .build()

            val request =
                PeriodicWorkRequestBuilder<CacheWorker>(2, TimeUnit.HOURS)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}