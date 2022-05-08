package com.jasonkhew96.wenku8.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jasonkhew96.wenku8.Constants

@Entity(tableName = Constants.NOVEL_CHAPTER_CONTENT_IMAGE_TABLE)
data class NovelChapterContentImage(@PrimaryKey(autoGenerate = true) val id: Int, val cid: Int, val image: String)
