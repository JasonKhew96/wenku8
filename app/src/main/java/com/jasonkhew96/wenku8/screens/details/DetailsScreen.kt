package com.jasonkhew96.wenku8.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jasonkhew96.wenku8.model.NovelChapter
import com.jasonkhew96.wenku8.model.NovelDetails
import com.jasonkhew96.wenku8.model.NovelVolume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    onChapterClick: (Int) -> Unit,
    aid: Int,
    novelDetailsViewModel: NovelDetailsViewModel = hiltViewModel(),
) {
    if (aid != 0 && novelDetailsViewModel.aid.value != aid) {
        novelDetailsViewModel.updateAid(aid)
    }

    Scaffold(topBar = {
        SmallTopAppBar(
            title = {
                if (novelDetailsViewModel.novelDetails.value.title != "") Text(novelDetailsViewModel.novelDetails.value.title) else Text(
                    "Loading..."
                )
            },
        )
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (aid != 0) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(novelDetailsViewModel.isRefreshing.value),
                    onRefresh = { novelDetailsViewModel.refresh(true) },
                ) {
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        DetailsComponent(aid, novelDetailsViewModel.novelDetails.value)
                        VolumesComponent(
                            volumes = novelDetailsViewModel.novelVolumes.value,
                            onClick = { cid ->
                                onChapterClick(cid)
                            })
                    }
                }
            } else {
                Text("aid: 0")
            }
        }
    }
}

@Composable
fun DetailsComponent(aid: Int, novelDetails: NovelDetails) {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        val id: Int = aid / 1000
        val cover = "https://img.wenku8.com/image/${id}/${aid}/${aid}s.jpg"

        AsyncImage(
            contentScale = ContentScale.FillWidth,
            model = ImageRequest.Builder(LocalContext.current).data(cover).crossfade(true).build(),
            modifier = Modifier.width(82.dp),
            contentDescription = novelDetails.title,
        )

        Column(modifier = Modifier.padding(start = 4.dp)) {
            Text(novelDetails.classType)
            Text(novelDetails.author)
            Text(novelDetails.status)
            Text(novelDetails.lastUpdate)
            Text(novelDetails.charCount)
            Text(novelDetails.tags)
            Text(novelDetails.hotStatus)
            Text(novelDetails.hotIndexStatus)
        }
    }
    Text(novelDetails.lastUpdatedChapter)
    Text(novelDetails.introduction)
    Text(novelDetails.animeStatus)
}

@Composable
fun VolumesComponent(volumes: List<NovelVolume>, onClick: (Int) -> Unit) {
    volumes.forEach { volume ->
        VolumeComponent(volume = volume, onClick = onClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeComponent(volume: NovelVolume, onClick: (Int) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Surface(shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    volume.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                if (expanded) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.arrow_down_float),
                        contentDescription = "collapse"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = android.R.drawable.arrow_up_float),
                        contentDescription = "expand"
                    )
                }

            }
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(8.dp)) {
                    volume.chapters.forEach { chapter ->
                        ChapterComponent(chapter, onClick)
                    }
                }
            }

        }

    }
}

@Composable
fun ChapterComponent(chapter: NovelChapter, onClick: (Int) -> Unit) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(chapter.cid) }
            .padding(8.dp),
    ) {
        Text(text = chapter.title, style = MaterialTheme.typography.titleMedium)
    }
}

//@Preview
//@Composable
//fun PreviewVolumes() {
//    val volumes: MutableList<Volume> = mutableListOf()
//    for (i in 1..3) {
//        val chapters: MutableList<Chapter> = mutableListOf()
//        for (j in 1..3) {
//            chapters.add(Chapter(cid = j, title = "Chapter $j"))
//        }
//        volumes.add(Volume(aid = i, title = "Volume $i", chapters = chapters))
//    }
//    VolumesComponent(volumes = volumes)
//}
