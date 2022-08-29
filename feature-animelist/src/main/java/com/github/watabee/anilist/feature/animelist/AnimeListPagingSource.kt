package com.github.watabee.anilist.feature.animelist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.watabee.anilist.core.data.repository.AnimeRepository
import com.github.watabee.anilist.core.model.AnimeList
import com.github.watabee.anilist.core.model.AnimeListType

class AnimeListPagingSource(
    private val animeListType: AnimeListType,
    private val animeRepository: AnimeRepository
) : PagingSource<Int, AnimeList.Media>() {
    override fun getRefreshKey(state: PagingState<Int, AnimeList.Media>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeList.Media> {
        val page = params.key ?: 1
        val perPage = params.loadSize
        return try {
            val animeList = getAnimeList(page, perPage)
            LoadResult.Page(
                prevKey = null,
                nextKey = if (animeList.pageInfo.hasNextPage) animeList.pageInfo.currentPage + 1 else null,
                data = animeList.mediaList
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    private suspend fun getAnimeList(page: Int, perPage: Int): AnimeList {
        return when (animeListType) {
            AnimeListType.TRENDING -> animeRepository.getTrendingAnimeList(page, perPage)
            AnimeListType.SEASON -> animeRepository.getCurrentSeasonAnimeList(page, perPage)
            AnimeListType.NEXT_SEASON -> animeRepository.getNextSeasonAnimeList(page, perPage)
            AnimeListType.POPULAR -> animeRepository.getPopularAnimeList(page, perPage)
            AnimeListType.TOP -> animeRepository.getTopAnimeList(page, perPage)
        }
    }
}
