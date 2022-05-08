package com.jasonkhew96.wenku8.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jasonkhew96.wenku8.Constants
import com.jasonkhew96.wenku8.model.NovelDetails

@Dao
interface NovelDetailsDao {
    @Query("SELECT * FROM ${Constants.NOVEL_DETAILS_TABLE}")
    fun getAll(): List<NovelDetails>

    @Query("SELECT * FROM ${Constants.NOVEL_DETAILS_TABLE} WHERE aid = :aid")
    fun get(aid: Int): List<NovelDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(novel: NovelDetails)

    @Query("DELETE FROM ${Constants.NOVEL_DETAILS_TABLE}")
    fun deleteAll()
}
