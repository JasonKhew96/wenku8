package com.jasonkhew96.wenku8.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jasonkhew96.wenku8.Constants.ITEMS_PER_PAGE
import com.jasonkhew96.wenku8.data.local.Wenku8Database
import com.jasonkhew96.wenku8.data.paging.TopListPagingSource
import com.jasonkhew96.wenku8.data.remote.Wenku8Service
import com.jasonkhew96.wenku8.model.Novel
import com.jasonkhew96.wenku8.model.NovelChapterContent
import com.jasonkhew96.wenku8.model.NovelChapterContentImage
import com.jasonkhew96.wenku8.model.NovelDetails
import com.jasonkhew96.wenku8.model.NovelVolume
import com.jasonkhew96.wenku8.utils.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

class Repository @Inject constructor(
    private val wenku8Service: Wenku8Service,
    private val wenku8Database: Wenku8Database,
) {
    fun getTopList(sort: String): Flow<PagingData<Novel>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { TopListPagingSource(wenku8Service, sort) },
        ).flow
    }

    suspend fun getNovelDetails(aid: Int, force: Boolean = false): NovelDetails {
        return withContext(Dispatchers.IO) {
            val cacheList = wenku8Database.novelDetailsDao().get(aid)

            if (cacheList.isEmpty() || force) {
                val novelDetails = Parser.parseNovelDetails(aid, wenku8Service.getBook(aid))
                wenku8Database.novelDetailsDao().insert(novelDetails)
                novelDetails
            } else {
                cacheList[0]
            }
        }
    }

    suspend fun getNovelVolumes(aid: Int, force: Boolean = false): List<NovelVolume> {
        return withContext(Dispatchers.IO) {
            val cacheList = wenku8Database.novelVolumeDao().get(aid)
            if (cacheList.isEmpty() || force) {
                val novelVolume =
                    Parser.parseNovelVolumes(aid, wenku8Service.getNovelVolume(aid / 1000, aid))
                wenku8Database.novelVolumeDao().delete(aid)
                wenku8Database.novelVolumeDao().insert(novelVolume)
                for (volume in novelVolume) {
                    wenku8Database.novelChapterDao().insert(volume.chapters)
                }
                novelVolume
            } else {
                for (volume in cacheList) {
                    volume.chapters = wenku8Database.novelChapterDao().get(aid)
                }
                cacheList
            }
        }
    }

    suspend fun getNovelChapterContent(
        aid: Int, cid: Int, force: Boolean = false
    ): NovelChapterContent {
        return withContext(Dispatchers.IO) {
            val cacheList = wenku8Database.novelChapterContentDao().get(cid)
            if (cacheList.isEmpty() || force) {
                val novelChapterContent = Parser.parseNovelChapterContent(
                    aid, cid, wenku8Service.getNovelChapterContent(aid / 1000, aid, cid)
                )
                wenku8Database.novelChapterContentDao().insert(novelChapterContent)
                wenku8Database.novelChapterContentImageDao().delete(cid)
                for (image in novelChapterContent.images) {
                    wenku8Database.novelChapterContentImageDao()
                        .insert(NovelChapterContentImage(0, cid, image))
                }
                novelChapterContent
            } else {
                val novelChapterContent = cacheList[0]
                val images = mutableListOf<String>()
                wenku8Database.novelChapterContentImageDao().get(cid).forEach {
                    images.add(it.image)
                }
                novelChapterContent.images = images
                novelChapterContent
            }
        }
    }

    suspend fun login(username: String, password: String): ResponseBody {
        return wenku8Service.login(username, password)
    }

    suspend fun getUserDetail(): ResponseBody {
        return wenku8Service.getUserDetail()
    }

    suspend fun logout(): ResponseBody {
        return wenku8Service.logout()
    }
}
