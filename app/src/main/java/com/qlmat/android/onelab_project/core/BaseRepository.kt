package com.qlmat.android.onelab_project.core

import android.util.Log
import com.qlmat.android.onelab_project.core.func.Result

open class BaseRepository {
    protected suspend fun <T: Any> apiCall(call: suspend () -> T): Result<Throwable, T> =
        try {
            Result.Success(call.invoke())
        } catch (throwable: Throwable) {
            Log.e("apiCall", "error", throwable)
            Result.Error(throwable)
        }
}