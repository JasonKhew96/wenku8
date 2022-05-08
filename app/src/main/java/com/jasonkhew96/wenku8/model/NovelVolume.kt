package com.jasonkhew96.wenku8.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.jasonkhew96.wenku8.Constants

@Entity(tableName = Constants.NOVEL_VOLUME_TABLE)
data class NovelVolume(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val aid: Int,
    val title: String,
    @Ignore var chapters: List<NovelChapter>
) {
    constructor(id: Int, aid: Int, title: String) : this(
        id = id, aid = aid, title = title, chapters = listOf()
    )

    @Ignore
    constructor(aid: Int, title: String, chapters: List<NovelChapter>) : this(
        id = 0, aid = aid, title = title, chapters = chapters
    )
}
