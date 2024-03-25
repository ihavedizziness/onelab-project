package com.qlmat.android.onelab_project.data.repository

import android.util.Log
import com.qlmat.android.onelab_project.core.NetworkChecker
import com.qlmat.android.onelab_project.core.func.State
import com.qlmat.android.onelab_project.data.api.MoviesApi
import com.qlmat.android.onelab_project.data.local.MoviesDao
import com.qlmat.android.onelab_project.data.mapper.toEntity
import com.qlmat.android.onelab_project.data.mapper.toMovie
import com.qlmat.android.onelab_project.data.mapper.toPresentation
import com.qlmat.android.onelab_project.domain.PopularMoviesRepository
import com.qlmat.android.onelab_project.presentation.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PopularMoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
    private val networkChecker: NetworkChecker
): PopularMoviesRepository {

    private val _moviesDataFlow = MutableStateFlow<State<List<Movie>>>(State.Initial)
    override val observeMoviesStateFlow: Flow<State<List<Movie>>>
        get() = _moviesDataFlow

    override suspend fun fetchPopularMovies(needToRefresh: Boolean) =
        withContext(Dispatchers.IO) {
            try {
                val cachedMovies = moviesDao.getAll()

                if (cachedMovies.isEmpty() || needToRefresh) {
                    _moviesDataFlow.emit(State.Loading)
                } else {
                    Log.d("PopularMoviesRepoTag", "Caching movies")
                    _moviesDataFlow.emit(State.Success(cachedMovies.map { it.toPresentation() }))
                }

                if (networkChecker.isConnected) {
                    Log.d("PopularMoviesRepoTag", "Internet is connected")
                    val movies = moviesApi.getPopularMovies().results
                    _moviesDataFlow.emit(State.Success(movies.map { it.toMovie() }))
                    moviesDao.clearAndInsert(movies.map { it.toEntity() })
                } else if (cachedMovies.isEmpty()) {
                    _moviesDataFlow.emit(State.Failure(Exception("No internet and no cache")))
                }
            } catch (throwable: Throwable) {
                _moviesDataFlow.emit(State.Failure(throwable))
            }
        }
}