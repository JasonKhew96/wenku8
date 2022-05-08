package com.jasonkhew96.wenku8.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jasonkhew96.wenku8.Constants

@Entity(tableName = Constants.NOVEL_DETAILS_TABLE)
data class NovelDetails(
    @PrimaryKey val aid: Int,
    val title: String,
    val classType: String,
    val author: String,
    val status: String,
    val lastUpdate: String,
    val charCount: String,
    val tags: String,
    val hotStatus: String,
    val hotIndexStatus: String,
    val lastUpdatedChapter: String,
    val introduction: String,
    val animeStatus: String,
) {
    constructor() : this(0, "", "", "", "", "", "", "", "", "", "", "", "")
}
