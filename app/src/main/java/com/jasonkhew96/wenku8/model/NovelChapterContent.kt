package com.jasonkhew96.wenku8.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.jasonkhew96.wenku8.Constants

@Entity(tableName = Constants.NOVEL_CHAPTER_CONTENT_TABLE)
data class NovelChapterContent(
    val aid: Int,
    @PrimaryKey val cid: Int,
    val title: String,
    val content: String,
    @Ignore var images: List<String>
) {
    constructor(aid: Int, cid: Int, title: String, content: String) : this(
        aid,
        cid,
        title,
        content,
        emptyList()
    )
    @Ignore
    constructor() : this(
        0,
        0,
        "",
        "",
        emptyList()
    )
}
