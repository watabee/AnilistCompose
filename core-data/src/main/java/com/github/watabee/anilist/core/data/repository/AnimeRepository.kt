package com.github.watabee.anilist.core.data.repository

import com.github.watabee.anilist.core.model.AnimeTop
import com.github.watabee.anilist.core.network.AnimeTopQuery
import com.github.watabee.anilist.core.network.NetworkDataSource
import com.github.watabee.anilist.core.network.fragment.Media
import javax.inject.Inject

class AnimeRepository @Inject internal constructor(private val networkDataSource: NetworkDataSource) {
    suspend fun getAnimeTop(): AnimeTop {
        return networkDataSource.getAnimeTop().asModel()
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
}
