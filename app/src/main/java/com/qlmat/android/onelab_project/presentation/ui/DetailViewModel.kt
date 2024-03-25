package com.qlmat.android.onelab_project.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qlmat.android.onelab_project.domain.FavoriteMoviesRepository
import com.qlmat.android.onelab_project.presentation.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: FavoriteMoviesRepository
): ViewModel() {

    fun addToFavorites(
        movie: Movie
    ) {
        viewModelScope.launch {
            repository.addToFavorites(movie)
        }
    }

}