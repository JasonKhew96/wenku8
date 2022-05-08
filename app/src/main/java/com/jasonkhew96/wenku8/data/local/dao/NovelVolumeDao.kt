package com.jasonkhew96.wenku8.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jasonkhew96.wenku8.Constants
import com.jasonkhew96.wenku8.model.NovelVolume

@Dao
interface NovelVolumeDao {
    @Query("SELECT * FROM ${Constants.NOVEL_VOLUME_TABLE}")
    fun getAll(): List<NovelVolume>

    @Query("SELECT * FROM ${Constants.NOVEL_VOLUME_TABLE} WHERE aid = :aid")
    fun get(aid: Int): List<NovelVolume>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(novelVolume: List<NovelVolume>)

    @Query("DELETE FROM ${Constants.NOVEL_VOLUME_TABLE} WHERE aid = :aid")
    fun delete(aid: Int)

    @Query("DELETE FROM ${Constants.NOVEL_VOLUME_TABLE}")
    fun deleteAll()
}
