package com.github.watabee.anilist.feature.animetop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.watabee.anilist.core.model.AnimeListType
import com.github.watabee.anilist.core.model.AnimeTop
import com.github.watabee.anilist.core.ui.ErrorItem
import com.github.watabee.anilist.core.ui.LoadingItem
import com.github.watabee.anilist.core.ui.MediaItem

@Composable
fun AnimeTopScreen(animeTopViewModel: AnimeTopViewModel = hiltViewModel(), onViewAllButtonClicked: (AnimeListType) -> Unit) {
    val uiState = animeTopViewModel.animeTopUiState

    LaunchedEffect(animeTopViewModel) {
        animeTopViewModel.getAnimeTop()
    }

    AnimeTopScreen(
        uiState = uiState,
        onRetryButtonClicked = animeTopViewModel::getAnimeTop,
        onViewAllButtonClicked = onViewAllButtonClicked
    )
}

@Composable
private fun AnimeTopScreen(uiState: AnimeTopUiState, onRetryButtonClicked: () -> Unit, onViewAllButtonClicked: (AnimeListType) -> Unit) {
    when (uiState) {
        AnimeTopUiState.Loading -> {
            LoadingItem(modifier = Modifier.fillMaxSize())
        }
        AnimeTopUiState.Error -> {
            ErrorItem(
                modifier = Modifier.fillMaxSize(),
                onRetryButtonClicked = onRetryButtonClicked
            )
        }
        is AnimeTopUiState.Success -> {
            AnimeTopScreen(animeTop = uiState.animeTop, onViewAllButtonClicked = onViewAllButtonClicked)
        }
    }
}

@Composable
private fun AnimeTopScreen(animeTop: AnimeTop, onViewAllButtonClicked: (AnimeListType) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item(key = "trending") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_trending),
                mediaList = animeTop.trending,
                onViewAllButtonClicked = { onViewAllButtonClicked(AnimeListType.TRENDING) }
            )
        }
        item(key = "season") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_season),
                mediaList = animeTop.season,
                onViewAllButtonClicked = { onViewAllButtonClicked(AnimeListType.SEASON) }
            )
        }
        item(key = "nextSeason") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_next_season),
                mediaList = animeTop.nextSeason,
                onViewAllButtonClicked = { onViewAllButtonClicked(AnimeListType.NEXT_SEASON) }
            )
        }
        item(key = "popular") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_popular),
                mediaList = animeTop.popular,
                onViewAllButtonClicked = { onViewAllButtonClicked(AnimeListType.POPULAR) }
            )
        }
        item(key = "top") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_top),
                mediaList = animeTop.top,
                onViewAllButtonClicked = { onViewAllButtonClicked(AnimeListType.TOP) }
            )
        }
    }
}

@Composable
private fun MediasCarousel(headerTitle: String, mediaList: List<AnimeTop.Media>, onViewAllButtonClicked: () -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(top = 24.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
                text = headerTitle,
                color = Color.DarkGray,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onViewAllButtonClicked) {
                Text(text = stringResource(id = R.string.feature_animetop_view_all_button_title))
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = mediaList, key = { it.id }) { media ->
                MediaItem(
                    modifier = Modifier.width(105.dp),
                    title = media.title,
                    coverImageUrl = media.coverImageUrl
                )
            }
        }
    }
}
