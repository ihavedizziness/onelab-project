package com.qlmat.android.onelab_project.domain

import com.qlmat.android.onelab_project.core.func.State
import com.qlmat.android.onelab_project.presentation.model.Movie
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {
    val observeMoviesStateFlow: Flow<State<List<Movie>>>
    suspend fun fetchPopularMovies(needToRefresh: Boolean)
}