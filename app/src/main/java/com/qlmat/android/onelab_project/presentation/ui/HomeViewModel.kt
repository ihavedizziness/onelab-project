package com.qlmat.android.onelab_project.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.qlmat.android.onelab_project.core.func.Resource
import com.qlmat.android.onelab_project.core.func.State
import com.qlmat.android.onelab_project.domain.PopularMoviesRepository
import com.qlmat.android.onelab_project.presentation.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PopularMoviesRepository
) : ViewModel() {

    private val _popularMoviesLiveData = repository.observeMoviesStateFlow
        .map(::mapToUiState)
        .asLiveData()
    val popularMoviesLiveData: LiveData<Resource<List<Movie>>> get() = _popularMoviesLiveData

    fun getPopularMovies(needToRefresh: Boolean) {
        viewModelScope.launch {
            repository.fetchPopularMovies(needToRefresh)
        }
    }

    fun refreshPopularMovies() {
        getPopularMovies(needToRefresh = true)
    }

    private fun mapToUiState(moviesState: State<List<Movie>>) =
        when (moviesState) {
            State.Initial -> Resource.Empty
            State.Loading -> Resource.Loading
            is State.Failure -> Resource.Error(moviesState.exception)
            is State.Success -> Resource.Success(moviesState.data)
        }

//    fun getPagedPopularMovies(): LiveData<PagingData<Movie>> {
//        return moviesPagingRepository.getPagedPopularMovies().cachedIn(viewModelScope)
//    }

}