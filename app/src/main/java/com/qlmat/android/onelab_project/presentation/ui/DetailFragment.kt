package com.qlmat.android.onelab_project.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.qlmat.android.onelab_project.R
import com.qlmat.android.onelab_project.databinding.FragmentDetailBinding
import com.qlmat.android.onelab_project.presentation.model.Movie
import dagger.hilt.android.AndroidEntryPoint

const val KEY_MOVIE_ID = "movie_id"
const val KEY_MOVIE_POSTER_URL = "movie_poster_url"
const val KEY_MOVIE_NAME = "movie_name"
const val KEY_MOVIE_RATING = "movie_rating"
const val KEY_MOVIE_OVERVIEW = "movie_description"
const val KEY_RELEASE_DATE = "movie_release_date"

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewBinding: FragmentDetailBinding by viewBinding()
    private val viewModel: DetailViewModel by viewModels()

    private var id: Long = 0
    private var posterUrl: String = ""
    private var name: String = ""
    private var rating: Double = 0.0
    private var overview: String = ""
    private var releaseDate: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = requireArguments().getLong(KEY_MOVIE_ID)
        posterUrl = requireArguments().getString(KEY_MOVIE_POSTER_URL)!!
        name = requireArguments().getString(KEY_MOVIE_NAME)!!
        rating = requireArguments().getDouble(KEY_MOVIE_RATING)
        overview = requireArguments().getString(KEY_MOVIE_OVERVIEW)!!
        releaseDate = requireArguments().getString(KEY_RELEASE_DATE)!!

        initViews()
    }

    private fun initViews() = with(viewBinding) {
        moviePosterImageView.load(
            "https://image.tmdb.org/t/p/original${posterUrl}\""
        ) {
            crossfade(true)
        }
        titleTextView.text = name
        ratingTextView.text = rating.toString()
        detailsTextView.text = overview

        addMovieButton.setOnClickListener {
            viewModel.addToFavorites(Movie(
                id = id,
                title = name,
                posterUrl = posterUrl,
                rating = rating,
                overview = overview,
                releaseDate = releaseDate
            ))
        }
    }

}