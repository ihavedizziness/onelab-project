package com.qlmat.android.onelab_project.data.api

import com.qlmat.android.onelab_project.data.model.PopularMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): PopularMoviesDto

}