package com.qlmat.android.onelab_project.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.onelab_project.R
import com.qlmat.android.onelab_project.core.func.Resource
import com.qlmat.android.onelab_project.databinding.FragmentHomeBinding
import com.qlmat.android.onelab_project.presentation.adapter.PopularMoviesAdapter
import com.qlmat.android.onelab_project.presentation.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewBinding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    private val scope = CoroutineScope(Dispatchers.Main)

    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObserver()

        viewModel.getPopularMovies(needToRefresh = false)
    }

    private fun initViews() = with(viewBinding) {
        popularMoviesAdapter = PopularMoviesAdapter { movie ->
            val bundle = Bundle().apply {
                putLong(KEY_MOVIE_ID, movie.id)
                putString(KEY_MOVIE_NAME, movie.title)
                putDouble(KEY_MOVIE_RATING, movie.rating)
                putString(KEY_MOVIE_OVERVIEW, movie.overview)
                putString(KEY_MOVIE_POSTER_URL, movie.posterUrl)
                putString(KEY_RELEASE_DATE, movie.releaseDate)
            }
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }

        popularMovieRecycler.apply {
            adapter = popularMoviesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPopularMovies()
        }
    }

    private fun initObserver() {
        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner, ::onGetPopularMovies)
    }

    private fun onGetPopularMovies(resource: Resource<List<Movie>>) = with(viewBinding) {
        val isLoading = resource is Resource.Loading

        popularMovieRecycler.isVisible = isLoading.not()
        progress.isVisible = isLoading

        when (resource) {
            is Resource.Success -> {
                popularMoviesAdapter.submitList(resource.data)
            }
            is Resource.Error -> {
                Log.e(
                    "MainFragmentTag",
                    "Failed to load popular movies",
                    resource.exception
                )
            }
            else -> Unit
        }
        swipeRefreshLayout.isRefreshing = false
    }

}