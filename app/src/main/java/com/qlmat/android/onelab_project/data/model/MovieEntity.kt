package com.qlmat.android.onelab_project.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qlmat.android.onelab_project.data.COLUMN_NAME_MOVIE_DESCRIPTION
import com.qlmat.android.onelab_project.data.COLUMN_NAME_MOVIE_ID
import com.qlmat.android.onelab_project.data.COLUMN_NAME_MOVIE_NAME
import com.qlmat.android.onelab_project.data.COLUMN_NAME_MOVIE_POSTER_URL
import com.qlmat.android.onelab_project.data.COLUMN_NAME_MOVIE_RATING
import com.qlmat.android.onelab_project.data.COLUMN_NAME_MOVIE_RELEASE_DATE
import com.qlmat.android.onelab_project.data.TABLE_NAME_MOVIES

@Entity(tableName = TABLE_NAME_MOVIES)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NAME_MOVIE_ID)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_NAME_MOVIE_NAME)
    val movieName: String,
    @ColumnInfo(name = COLUMN_NAME_MOVIE_DESCRIPTION)
    val movieDescription: String,
    @ColumnInfo(name = COLUMN_NAME_MOVIE_POSTER_URL)
    val moviePosterUrl: String,
    @ColumnInfo(name = COLUMN_NAME_MOVIE_RELEASE_DATE)
    val movieReleaseDate: String,
    @ColumnInfo(name = COLUMN_NAME_MOVIE_RATING)
    val movieRating: Double
)

data class SomeClass(
    val str: String,
    val num: Int
)

enum class SomeStatus {
    SET, NOT_SET, REFUSED
}