package com.qlmat.android.onelab_project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qlmat.android.onelab_project.data.model.FavoriteMovieEntity
import com.qlmat.android.onelab_project.data.model.MovieEntity

@Database(
    entities = [
        MovieEntity::class,
        FavoriteMovieEntity::class
    ],
    version = 3
)
@TypeConverters(
    CustomConverter::class
)
abstract class MyRoomDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun favouritesDao(): FavoritesDao
}