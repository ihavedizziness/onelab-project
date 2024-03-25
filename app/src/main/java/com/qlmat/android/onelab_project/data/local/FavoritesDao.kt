package com.qlmat.android.onelab_project.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qlmat.android.onelab_project.data.TABLE_NAME_FAVORITES
import com.qlmat.android.onelab_project.data.model.FavoriteMovieEntity

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM $TABLE_NAME_FAVORITES")
    suspend fun getAll(): List<FavoriteMovieEntity>

    @Insert
    suspend fun insert(entity: FavoriteMovieEntity)

    @Query("DELETE FROM $TABLE_NAME_FAVORITES")
    suspend fun deleteAll()

}