package com.jasonkhew96.wenku8.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jasonkhew96.wenku8.Constants

@Entity(tableName = Constants.NOVEL_CHAPTER_TABLE)
data class NovelChapter(val aid: Int, @PrimaryKey val cid: Int, val title: String)
