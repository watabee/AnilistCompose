package com.github.watabee.anilist.feature.animelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.watabee.anilist.core.ui.ErrorItem
import com.github.watabee.anilist.core.ui.LoadingItem
import com.github.watabee.anilist.core.ui.MediaItem
import com.github.watabee.anilist.core.ui.items

@Composable
fun AnimeListScreen(viewModel: AnimeListViewModel = hiltViewModel()) {
    val lazyPagingItems = viewModel.pagerFlow.collectAsLazyPagingItems()

    when (lazyPagingItems.loadState.refresh) {
        LoadState.Loading -> {
            LoadingItem(modifier = Modifier.fillMaxSize())
        }
        is LoadState.Error -> {
            ErrorItem(modifier = Modifier.fillMaxSize()) {
                lazyPagingItems.retry()
            }
        }
        is LoadState.NotLoading -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 120.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
            ) {
                items(lazyPagingItems) { media ->
                    if (media != null) {
                        MediaItem(
                            modifier = Modifier.width(120.dp),
                            title = media.title,
                            coverImageUrl = media.coverImageUrl
                        )
                    }
                }
                when (lazyPagingItems.loadState.append) {
                    LoadState.Loading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            LoadingItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            )
                        }
                    }
                    is LoadState.Error -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            ErrorItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            ) {
                                lazyPagingItems.retry()
                            }
                        }
                    }
                    is LoadState.NotLoading -> {
                        // do nothing.
                    }
                }
            }
        }
    }
}
