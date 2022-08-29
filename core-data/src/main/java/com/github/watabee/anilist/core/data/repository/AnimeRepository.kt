package com.github.watabee.anilist.core.data.repository

import com.github.watabee.anilist.core.model.AnimeList
import com.github.watabee.anilist.core.model.AnimeTop
import com.github.watabee.anilist.core.model.PageInfo
import com.github.watabee.anilist.core.network.AnimeListQuery
import com.github.watabee.anilist.core.network.AnimeTopQuery
import com.github.watabee.anilist.core.network.NetworkDataSource
import com.github.watabee.anilist.core.network.fragment.Media
import javax.inject.Inject

class AnimeRepository @Inject internal constructor(private val networkDataSource: NetworkDataSource) {
    suspend fun getAnimeTop(): AnimeTop {
        return networkDataSource.getAnimeTop().asModel()
    }

    suspend fun getTrendingAnimeList(page: Int, perPage: Int): AnimeList {
        return networkDataSource.getTrendingAnimeList(page, perPage).asModel()
    }

    suspend fun getCurrentSeasonAnimeList(page: Int, perPage: Int): AnimeList {
        return networkDataSource.getCurrentSeasonAnimeList(page, perPage).asModel()
    }

    suspend fun getNextSeasonAnimeList(page: Int, perPage: Int): AnimeList {
        return networkDataSource.getNextSeasonAnimeList(page, perPage).asModel()
    }

    suspend fun getPopularAnimeList(page: Int, perPage: Int): AnimeList {
        return networkDataSource.getPopularAnimeList(page, perPage).asModel()
    }

    suspend fun getTopAnimeList(page: Int, perPage: Int): AnimeList {
        return networkDataSource.getTopAnimeList(page, perPage).asModel()
    }

    private fun AnimeTopQuery.Data.asModel(): AnimeTop =
        AnimeTop(
            trending = trending.media.mapNotNull { it?.media?.asModel() },
            season = season.media.mapNotNull { it?.media?.asModel() },
            nextSeason = nextSeason.media.mapNotNull { it?.media?.asModel() },
            popular = popular.media.mapNotNull { it?.media?.asModel() },
            top = top.media.mapNotNull { it?.media?.asModel() }
        )

    private fun Media.asModel(): AnimeTop.Media =
        AnimeTop.Media(
            id = id,
            title = title?.userPreferred,
            coverImageUrl = coverImage?.large,
            format = format?.rawValue,
            genres = genres?.filterNotNull().orEmpty(),
            mainStudio = studios?.edges?.firstOrNull()?.node?.name
        )

    private fun AnimeListQuery.Data.asModel(): AnimeList =
        AnimeList(
            pageInfo = page.pageInfo.let {
                PageInfo(
                    total = it.total,
                    perPage = it.perPage,
                    currentPage = it.currentPage,
                    lastPage = it.lastPage,
                    hasNextPage = it.hasNextPage
                )
            },
            mediaList = page.media.mapNotNull { it?.asModel() }
        )

    private fun AnimeListQuery.Medium.asModel(): AnimeList.Media =
        AnimeList.Media(id = id, title = title?.userPreferred, coverImageUrl = coverImage?.large)
}
