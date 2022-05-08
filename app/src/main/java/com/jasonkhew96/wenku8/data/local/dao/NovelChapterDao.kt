package com.jasonkhew96.wenku8.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jasonkhew96.wenku8.Constants
import com.jasonkhew96.wenku8.model.NovelChapter

@Dao
interface NovelChapterDao {
    @Query("SELECT * FROM ${Constants.NOVEL_CHAPTER_TABLE}")
    fun getAll(): List<NovelChapter>

    @Query("SELECT * FROM ${Constants.NOVEL_CHAPTER_TABLE} WHERE aid = :aid")
    fun get(aid: Int): List<NovelChapter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(novelChapter: List<NovelChapter>)

    @Query("DELETE FROM ${Constants.NOVEL_CHAPTER_TABLE}")
    fun deleteAll()
}
