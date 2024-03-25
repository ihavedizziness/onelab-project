package com.qlmat.android.onelab_project.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.qlmat.android.onelab_project.core.func.Resource
import com.qlmat.android.onelab_project.core.func.State
import com.qlmat.android.onelab_project.domain.FavoriteMoviesRepository
import com.qlmat.android.onelab_project.presentation.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteMoviesRepository
) : ViewModel() {

    private val _favoriteMoviesLiveData = repository.observeFavoritesStateFlow
        .map(::mapToUiState)
        .asLiveData()
    val favoriteMoviesLiveData: LiveData<Resource<List<Movie>>> get() = _favoriteMoviesLiveData

    fun getFavoriteMovies() {
        viewModelScope.launch {
            repository.fetchFavoriteMovies()
        }
    }

    private fun mapToUiState(favoritesState: State<List<Movie>>) =
        when (favoritesState) {
            State.Initial -> Resource.Empty
            State.Loading -> Resource.Loading
            is State.Failure -> Resource.Error(favoritesState.exception)
            is State.Success -> Resource.Success(favoritesState.data)
        }
}