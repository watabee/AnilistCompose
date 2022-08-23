package com.github.watabee.anilist.feature.animetop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.github.watabee.anilist.core.designsystem.theme.AnilistTheme
import com.github.watabee.anilist.core.model.AnimeTop

@Composable
fun AnimeTopScreen(animeTopViewModel: AnimeTopViewModel = hiltViewModel()) {
    val uiState = animeTopViewModel.animeTopUiState

    LaunchedEffect(animeTopViewModel) {
        animeTopViewModel.getAnimeTop()
    }

    AnimeTopScreen(
        uiState = uiState,
        onRetryButtonClicked = animeTopViewModel::getAnimeTop
    )
}

@Composable
private fun AnimeTopScreen(uiState: AnimeTopUiState, onRetryButtonClicked: () -> Unit) {
    when (uiState) {
        AnimeTopUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        AnimeTopUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = onRetryButtonClicked
                ) {
                    Text(text = stringResource(R.string.feature_animetop_retry_button_title))
                }
            }
        }
        is AnimeTopUiState.Success -> {
            AnimeTopScreen(animeTop = uiState.animeTop)
        }
    }
}

@Composable
private fun AnimeTopScreen(animeTop: AnimeTop) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item(key = "trending") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_trending),
                mediaList = animeTop.trending
            )
        }
        item(key = "season") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_season),
                mediaList = animeTop.season
            )
        }
        item(key = "nextSeason") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_next_season),
                mediaList = animeTop.nextSeason
            )
        }
        item(key = "popular") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_popular),
                mediaList = animeTop.popular
            )
        }
        item(key = "top") {
            MediasCarousel(
                headerTitle = stringResource(R.string.feature_animetop_header_top),
                mediaList = animeTop.top
            )
        }
    }
}

@Composable
private fun MediasCarousel(headerTitle: String, mediaList: List<AnimeTop.Media>) {
    Column {
        Text(
            modifier = Modifier.padding(top = 24.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
            text = headerTitle,
            color = Color.DarkGray,
            style = MaterialTheme.typography.h6
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = mediaList, key = { it.id }) { media ->
                MediaContent(media = media)
            }
        }
    }
}

@Composable
private fun MediaContent(modifier: Modifier = Modifier, media: AnimeTop.Media) {
    Column(modifier = modifier.width(IntrinsicSize.Min)) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(width = 105.dp, height = 150.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = media.coverImageUrl,
            contentDescription = null
        ) {
            when (painter.state) {
                AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Error,
                is AsyncImagePainter.State.Loading -> {
                    Box(modifier = Modifier.background(color = Color(red = 221, green = 230, blue = 238, alpha = 255)))
                }
                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = media.title.orEmpty(),
            color = Color.Gray,
            style = MaterialTheme.typography.subtitle2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun PreviewMediaContent() {
    AnilistTheme {
        MediaContent(
            media = AnimeTop.Media(
                id = 1,
                title = "Youkoso Jitsuryoku Shijou Shugi no Kyoushitsu e 2nd Season",
                coverImageUrl = null,
                format = null,
                genres = emptyList(),
                mainStudio = null
            )
        )
    }
}
