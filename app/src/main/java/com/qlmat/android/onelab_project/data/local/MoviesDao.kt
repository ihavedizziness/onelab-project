package com.qlmat.android.onelab_project.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.qlmat.android.onelab_project.data.TABLE_NAME_MOVIES
import com.qlmat.android.onelab_project.data.model.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM $TABLE_NAME_MOVIES")
    suspend fun getAll(): List<MovieEntity>

    @Insert
    suspend fun insert(entities: List<MovieEntity>)

    @Query("DELETE FROM $TABLE_NAME_MOVIES")
    suspend fun deleteAll()

    @Transaction
    suspend fun clearAndInsert(entities: List<MovieEntity>) {
        deleteAll()
        insert(entities)
    }
}