package com.qlmat.android.onelab_project.data.mapper

import com.qlmat.android.onelab_project.data.model.FavoriteMovieEntity
import com.qlmat.android.onelab_project.data.model.MovieDto
import com.qlmat.android.onelab_project.data.model.MovieEntity
import com.qlmat.android.onelab_project.presentation.model.Movie

internal fun MovieDto.toMovie() =
    Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath,
        releaseDate = releaseDate,
        rating = voteAverage
    )

internal fun MovieDto.toEntity() =
    MovieEntity(
        movieId = id,
        movieName = title,
        movieDescription = overview,
        moviePosterUrl = posterPath,
        movieReleaseDate = releaseDate,
        movieRating = voteAverage
    )

internal fun MovieEntity.toPresentation() =
    Movie(
        id = movieId,
        title = movieName,
        overview = movieDescription,
        posterUrl = moviePosterUrl,
        releaseDate = movieReleaseDate,
        rating = movieRating
    )

internal fun FavoriteMovieEntity.toPresentation() =
    Movie(
        id = movieId,
        title = movieName,
        overview = movieDescription,
        posterUrl = moviePosterUrl,
        releaseDate = movieReleaseDate,
        rating = movieRating
    )

internal fun Movie.toFavoriteMovieEntity() =
    FavoriteMovieEntity(
        movieId = id,
        movieName = title,
        movieDescription = overview,
        moviePosterUrl = posterUrl,
        movieReleaseDate = releaseDate,
        movieRating = rating
    )