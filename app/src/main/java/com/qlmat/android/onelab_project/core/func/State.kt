package com.qlmat.android.onelab_project.core.func

sealed class State<out Data> {
    class Failure(val exception: Throwable): State<Nothing>()
    data object Loading: State<Nothing>()
    data object Initial: State<Nothing>()
    class Success<out Data>(val data: Data): State<Data>()
}