package com.jasonkhew96.wenku8.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jasonkhew96.wenku8.data.remote.Wenku8Service
import com.jasonkhew96.wenku8.model.Novel
import com.jasonkhew96.wenku8.utils.Parser

class TopListPagingSource(private val wenku8Service: Wenku8Service, private val sort: String) :
    PagingSource<Int, Novel>() {
    override fun getRefreshKey(state: PagingState<Int, Novel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Novel> {
        val currentPage = params.key ?: 1
        return try {
            val novels =
                Parser.parseTopList(wenku8Service.getTopList(page = currentPage, sort = sort))
            val endOfPaginationReached = novels.isEmpty()
            if (novels.isNotEmpty()) {
                LoadResult.Page(
                    data = novels,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(), prevKey = null, nextKey = null
                )
            }
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}
