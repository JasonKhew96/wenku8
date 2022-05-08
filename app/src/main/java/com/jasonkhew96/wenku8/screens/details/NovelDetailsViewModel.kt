package com.jasonkhew96.wenku8.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasonkhew96.wenku8.data.repository.Repository
import com.jasonkhew96.wenku8.model.NovelDetails
import com.jasonkhew96.wenku8.model.NovelVolume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NovelDetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing = _isRefreshing

    private val _aid = mutableStateOf(0)
    val aid = _aid

    private val _novelDetails = mutableStateOf(NovelDetails())
    val novelDetails = _novelDetails

    private val _novelVolumes = mutableStateOf(listOf<NovelVolume>())
    val novelVolumes = _novelVolumes

    fun updateAid(aid: Int) {
        _aid.value = aid
        refresh(false)
    }

    fun refresh(force: Boolean) {
        viewModelScope.launch {
            _isRefreshing.value = true
            _novelDetails.value = repository.getNovelDetails(_aid.value, force)
            _novelVolumes.value = repository.getNovelVolumes(_aid.value, force)
            _isRefreshing.value = false
        }
    }
}

