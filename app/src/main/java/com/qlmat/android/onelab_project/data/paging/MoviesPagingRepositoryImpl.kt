package com.qlmat.android.onelab_project.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.qlmat.android.onelab_project.core.NetworkChecker
import com.qlmat.android.onelab_project.data.api.MoviesApi
import com.qlmat.android.onelab_project.presentation.model.Movie
import javax.inject.Inject

class MoviesPagingRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val networkChecker: NetworkChecker
): MoviesPagingRepository {

    override fun getPagedPopularMovies(): LiveData<PagingData<Movie>> {
        return Pager(
            PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                enablePlaceholders = false
            )
        ) {
            MoviesPagingSource(moviesApi, networkChecker)
        }.liveData
    }

}