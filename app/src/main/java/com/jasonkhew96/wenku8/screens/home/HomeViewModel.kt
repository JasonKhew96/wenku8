package com.jasonkhew96.wenku8.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jasonkhew96.wenku8.data.repository.Repository
import com.jasonkhew96.wenku8.model.Novel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _sortType = mutableStateOf("lastupdate")
    val sortType = _sortType

    private val _topList = MutableStateFlow<PagingData<Novel>>(PagingData.empty())
    val topList = _topList

    fun updateSort(sort: String) {
        _sortType.value = sort
        loadTopList(sort)
    }

    init {
        loadTopList("lastupdate")
    }

    private fun loadTopList(sort: String) {
        viewModelScope.launch {
            repository.getTopList(sort = sort).cachedIn(viewModelScope).collect {
                _topList.value = it
            }
        }
    }
}
