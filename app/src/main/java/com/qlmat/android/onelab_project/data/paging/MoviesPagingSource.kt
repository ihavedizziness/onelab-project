package com.qlmat.android.onelab_project.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.qlmat.android.onelab_project.core.NetworkChecker
import com.qlmat.android.onelab_project.data.api.MoviesApi
import com.qlmat.android.onelab_project.data.mapper.toMovie
import com.qlmat.android.onelab_project.presentation.model.Movie
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val moviesApi: MoviesApi,
    private val networkChecker: NetworkChecker
) : PagingSource<Int, Movie>() {

    companion object {
        private const val INITIAL_PAGE_POS = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pagePos = params.key ?: INITIAL_PAGE_POS

            if (networkChecker.isConnected) {
                val movies = moviesApi.getPopularMovies().results.map { it.toMovie() }
                val nextPagePos = if (movies.isEmpty()) null else pagePos + 1
                val prevPagePos = if (pagePos > 1) pagePos - 1 else null
                LoadResult.Page(movies, prevPagePos, nextPagePos)
            } else {
                LoadResult.Error(Exception("Error during fetching data"))
            }
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

}