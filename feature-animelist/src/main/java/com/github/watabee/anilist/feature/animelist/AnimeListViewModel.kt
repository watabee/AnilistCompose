package com.github.watabee.anilist.feature.animelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.watabee.anilist.core.data.repository.AnimeRepository
import com.github.watabee.anilist.core.model.AnimeList
import com.github.watabee.anilist.core.model.AnimeListType
import com.github.watabee.anilist.feature.animelist.navigation.AnimeListNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    animeRepository: AnimeRepository
) : ViewModel() {

    private val animeListType: AnimeListType =
        savedStateHandle.get<String>(AnimeListNavigation.argAnimeListType)?.let(AnimeListType::valueOf)!!

    val pagerFlow: Flow<PagingData<AnimeList.Media>> =
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, initialLoadSize = 20),
            pagingSourceFactory = { AnimeListPagingSource(animeListType, animeRepository) }
        ).flow.cachedIn(viewModelScope)
}
