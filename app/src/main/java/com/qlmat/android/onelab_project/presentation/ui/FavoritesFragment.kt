package com.qlmat.android.onelab_project.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.qlmat.android.onelab_project.core.func.Resource
import com.qlmat.android.onelab_project.presentation.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ComposeView(requireContext())
        view.apply {
            setContent {
                MyApp()
            }
        }
        return view
    }

}

@Composable
fun MyApp(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesState by viewModel.favoriteMoviesLiveData.observeAsState()
    
    when (val resource = favoritesState) {
        is Resource.Success -> {
            FavoritesScreen(movies = resource.data)
        }
        is Resource.Error -> {
            Log.e(
                "FavoritesFragmentTag",
                "Failed to fetch favorite movies",
                resource.exception
            )
        }
        else -> Unit
    }

    viewModel.getFavoriteMovies()
}

@Composable
fun FavoritesScreen(movies: List<Movie>) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie = movie)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original${movie.posterUrl}\"",
                contentDescription = null,
                modifier = Modifier.size(100.dp),
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(text = movie.title)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = movie.releaseDate)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = movie.overview)
            }
        }
    }
}