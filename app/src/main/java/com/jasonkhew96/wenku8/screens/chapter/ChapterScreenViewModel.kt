package com.jasonkhew96.wenku8.screens.chapter

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasonkhew96.wenku8.data.repository.Repository
import com.jasonkhew96.wenku8.model.NovelChapterContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterScreenViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing = _isRefreshing

    private val _aid = mutableStateOf(0)
    val aid = _aid

    private val _cid = mutableStateOf(0)
    val cid = _cid

    private val _chapterContent = mutableStateOf(NovelChapterContent())
    val chapterContent = _chapterContent

    fun updateIds(aid: Int, cid: Int) {
        _aid.value = aid
        _cid.value = cid
        refresh(false)
    }

    fun refresh(force: Boolean) {
        viewModelScope.launch {
            _isRefreshing.value = true
            _chapterContent.value = repository.getNovelChapterContent(_aid.value, _cid.value, force)
            _isRefreshing.value = false
        }
    }
}
