package com.jasonkhew96.wenku8.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jasonkhew96.wenku8.Constants
import com.jasonkhew96.wenku8.model.NovelChapterContent

@Dao
interface NovelChapterContentDao {
    @Query("SELECT * FROM ${Constants.NOVEL_CHAPTER_CONTENT_TABLE}")
    fun getAll(): List<NovelChapterContent>

    @Query("SELECT * FROM ${Constants.NOVEL_CHAPTER_CONTENT_TABLE} WHERE cid = :cid")
    fun get(cid: Int): List<NovelChapterContent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(novelChapterContents: NovelChapterContent)

    @Query("DELETE FROM ${Constants.NOVEL_CHAPTER_CONTENT_TABLE}")
    fun deleteAll()
}
