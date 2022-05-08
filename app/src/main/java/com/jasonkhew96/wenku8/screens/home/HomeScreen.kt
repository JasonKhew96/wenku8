package com.jasonkhew96.wenku8.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasonkhew96.wenku8.Constants


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNovelClick: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val sortType by homeViewModel.sortType
    val topList = homeViewModel.topList.collectAsLazyPagingItems()

    var showSortMenu by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        SmallTopAppBar(
            title = { Constants.sortMap[sortType]?.let { Text(it) } },
            actions = {
                IconButton(onClick = { showSortMenu = !showSortMenu }) {
                    Icon(Icons.Default.MoreVert, "More")
                }
                DropdownMenu(expanded = showSortMenu, onDismissRequest = { showSortMenu = false }) {
                    Constants.sortMap.forEach { sortType ->
                        DropdownMenuItem(text = { Text(sortType.value) }, onClick = {
                            homeViewModel.updateSort(sortType.key)
                            showSortMenu = false
                        })
                    }
                }
            },
        )
    }) { padding ->
        LazyColumn(state = rememberLazyListState(), modifier = Modifier.padding(padding)) {
            if (topList.loadState.refresh == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }

            itemsIndexed(topList) { _, item ->
                if (item == null) return@itemsIndexed
                val id: Int = item.aid / 1000
                val cover = "https://img.wenku8.com/image/${id}/${item.aid}/${item.aid}s.jpg"
                Surface(shape = RoundedCornerShape(8.dp),
                    shadowElevation = 8.dp,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    onClick = {
                        onNovelClick(item.aid)
                    }) {
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        AsyncImage(
                            contentScale = ContentScale.FillWidth,
                            model = ImageRequest.Builder(LocalContext.current).data(cover)
                                .crossfade(true).build(),
                            modifier = Modifier.width(82.dp),
                            contentDescription = item.title,
                        )
                        Column(modifier = Modifier.padding(start = 4.dp)) {
                            Text(
                                item.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    item.author,
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    item.classType,
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    item.lastUpdate,
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    item.charCount,
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    item.status,
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    item.tags,
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            Text(item.description)
                        }
                    }
                }

            }

            if (topList.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}
