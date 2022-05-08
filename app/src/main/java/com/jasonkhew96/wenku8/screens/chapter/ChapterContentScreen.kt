package com.jasonkhew96.wenku8.screens.chapter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterContentScreen(
    aid: Int, cid: Int, chapterScreenViewModel: ChapterScreenViewModel = hiltViewModel()
) {
    if (aid != 0 && cid != 0 && chapterScreenViewModel.aid.value != aid && chapterScreenViewModel.cid.value != cid) {
        chapterScreenViewModel.updateIds(aid, cid)
    }

    Scaffold(topBar = {
        SmallTopAppBar(
            title = {
                if (chapterScreenViewModel.chapterContent.value.title != "") Text(
                    chapterScreenViewModel.chapterContent.value.title
                ) else Text(
                    "Loading..."
                )
            },
        )
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            val list = chapterScreenViewModel.chapterContent.value.content.split("\n").map { line ->
                line.trim()
            }.filter { line ->
                line.isNotBlank()
            }

            SwipeRefresh(
                state = rememberSwipeRefreshState(chapterScreenViewModel.isRefreshing.value),
                onRefresh = { chapterScreenViewModel.refresh(true) }) {
                LazyColumn {
                    items(list) { line ->
                        Text(
                            text = line,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    items(chapterScreenViewModel.chapterContent.value.images) { image ->
                        AsyncImage(
                            contentScale = ContentScale.FillWidth,
                            model = ImageRequest.Builder(LocalContext.current).data(image)
                                .crossfade(true).build(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 4.dp),
                            contentDescription = "Image",
                        )
                    }
                }
            }
        }
    }
}
