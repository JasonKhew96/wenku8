package com.jasonkhew96.wenku8.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jasonkhew96.wenku8.Constants
import com.jasonkhew96.wenku8.model.NovelChapterContentImage

@Dao
interface NovelChapterContentImageDao {

    @Query("SELECT * FROM ${Constants.NOVEL_CHAPTER_CONTENT_IMAGE_TABLE}")
    fun getAll(): List<NovelChapterContentImage>

    @Query("SELECT * FROM ${Constants.NOVEL_CHAPTER_CONTENT_IMAGE_TABLE} WHERE cid = :cid")
    fun get(cid: Int): List<NovelChapterContentImage>

    @Query("DELETE FROM ${Constants.NOVEL_CHAPTER_CONTENT_IMAGE_TABLE} WHERE cid = :cid")
    fun delete(cid: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(novelChapterContentImage: NovelChapterContentImage)

    @Query("DELETE FROM ${Constants.NOVEL_CHAPTER_CONTENT_IMAGE_TABLE}")
    fun deleteAll()
}
