package com.qlmat.android.onelab_project.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.qlmat.android.onelab_project.presentation.model.Movie

interface MoviesPagingRepository {
    fun getPagedPopularMovies(): LiveData<PagingData<Movie>>
}