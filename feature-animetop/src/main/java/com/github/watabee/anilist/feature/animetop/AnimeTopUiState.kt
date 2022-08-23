package com.github.watabee.anilist.feature.animetop

import androidx.compose.runtime.Stable
import com.github.watabee.anilist.core.model.AnimeTop

@Stable
sealed interface AnimeTopUiState {
    object Loading : AnimeTopUiState

    object Error : AnimeTopUiState

    data class Success(
        val animeTop: AnimeTop
    ) : AnimeTopUiState
}
