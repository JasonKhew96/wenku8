package com.jasonkhew96.wenku8.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jasonkhew96.wenku8.data.local.dao.NovelChapterContentDao
import com.jasonkhew96.wenku8.data.local.dao.NovelChapterContentImageDao
import com.jasonkhew96.wenku8.data.local.dao.NovelChapterDao
import com.jasonkhew96.wenku8.data.local.dao.NovelDetailsDao
import com.jasonkhew96.wenku8.data.local.dao.NovelVolumeDao
import com.jasonkhew96.wenku8.model.NovelChapter
import com.jasonkhew96.wenku8.model.NovelChapterContent
import com.jasonkhew96.wenku8.model.NovelChapterContentImage
import com.jasonkhew96.wenku8.model.NovelDetails
import com.jasonkhew96.wenku8.model.NovelVolume

@Database(
    entities = [NovelDetails::class, NovelVolume::class, NovelChapter::class, NovelChapterContent::class, NovelChapterContentImage::class],
    version = 1
)
abstract class Wenku8Database : RoomDatabase() {
    abstract fun novelDetailsDao(): NovelDetailsDao
    abstract fun novelVolumeDao(): NovelVolumeDao
    abstract fun novelChapterDao(): NovelChapterDao
    abstract fun novelChapterContentDao(): NovelChapterContentDao
    abstract fun novelChapterContentImageDao(): NovelChapterContentImageDao
}
