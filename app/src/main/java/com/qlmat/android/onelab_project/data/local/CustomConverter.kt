package com.qlmat.android.onelab_project.data.local

import androidx.room.TypeConverter
import com.qlmat.android.onelab_project.presentation.model.Movie
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class CustomConverter {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromMovieList(movies: List<Movie>?): String {
        if (movies == null) return ""
        return json.encodeToString(ListSerializer(Movie.serializer()), movies)
    }

    @TypeConverter
    fun toMovieList(movieString: String?): List<Movie> {
        if (movieString.isNullOrEmpty()) return emptyList()
        return json.decodeFromString(ListSerializer(Movie.serializer()), movieString)
    }

    data class SomeClass(
        val str: String,
        val num: Int
    )

    enum class SomeStatus {
        SET, NOT_SET, REFUSED
    }

}