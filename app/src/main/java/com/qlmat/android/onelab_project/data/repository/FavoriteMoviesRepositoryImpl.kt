package com.qlmat.android.onelab_project.data.repository

import android.util.Log
import com.qlmat.android.onelab_project.core.func.State
import com.qlmat.android.onelab_project.data.local.FavoritesDao
import com.qlmat.android.onelab_project.data.mapper.toFavoriteMovieEntity
import com.qlmat.android.onelab_project.data.mapper.toPresentation
import com.qlmat.android.onelab_project.domain.FavoriteMoviesRepository
import com.qlmat.android.onelab_project.presentation.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteMoviesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao,
) : FavoriteMoviesRepository {

    private val _favoritesDataFlow = MutableStateFlow<State<List<Movie>>>(State.Initial)
    override val observeFavoritesStateFlow: Flow<State<List<Movie>>>
        get() = _favoritesDataFlow

    override suspend fun fetchFavoriteMovies() =
        withContext(Dispatchers.IO) {
            try {
                val cachedFavorites = favoritesDao.getAll()
                if (cachedFavorites.isEmpty()) {
                    _favoritesDataFlow.emit(State.Loading)
                } else {
                    Log.d("FavoriteMoviesRepoTag", "Caching movies")
                    _favoritesDataFlow.emit(State.Success(cachedFavorites.map { it.toPresentation() }))
                }
            } catch (throwable: Throwable) {
                _favoritesDataFlow.emit(State.Failure(throwable))
            }
        }

    override suspend fun addToFavorites(movie: Movie) =
        withContext(Dispatchers.IO) {
            try {
                val favoriteMovieEntity = movie.toFavoriteMovieEntity()
                favoritesDao.insert(favoriteMovieEntity)
                Log.d("FavoriteMoviesRepoTag" ,"Added $movie")
            } catch (throwable: Throwable) {
                Log.e("FavoriteMoviesRepoTag", "Error adding to favorites", throwable)
            }
        }
}