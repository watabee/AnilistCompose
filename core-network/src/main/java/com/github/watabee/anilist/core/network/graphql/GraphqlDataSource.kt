package com.github.watabee.anilist.core.network.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.github.watabee.anilist.core.network.AnimeListQuery
import com.github.watabee.anilist.core.network.AnimeTopQuery
import com.github.watabee.anilist.core.network.NetworkDataSource
import com.github.watabee.anilist.core.network.type.MediaSeason
import com.github.watabee.anilist.core.network.type.MediaSort
import javax.inject.Inject

internal class GraphqlDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : NetworkDataSource {
    override suspend fun getAnimeTop(): AnimeTopQuery.Data {
        // TODO: Set parameters dynamically
        val query = AnimeTopQuery(
            season = MediaSeason.SUMMER,
            seasonYear = 2022,
            nextSeason = MediaSeason.FALL,
            nextSeasonYear = 2022
        )
        return apolloClient.query(query).execute().dataAssertNoErrors
    }

    override suspend fun getTrendingAnimeList(page: Int, perPage: Int): AnimeListQuery.Data {
        val query = AnimeListQuery(
            page = page,
            perPage = perPage,
            sort = Optional.Present(listOf(MediaSort.TRENDING_DESC))
        )
        return apolloClient.query(query).execute().dataAssertNoErrors
    }

    override suspend fun getCurrentSeasonAnimeList(page: Int, perPage: Int): AnimeListQuery.Data {
        // TODO: Set parameters dynamically
        val query = AnimeListQuery(
            page = page,
            perPage = perPage,
            season = Optional.Present(MediaSeason.SUMMER),
            seasonYear = Optional.Present(2022),
            sort = Optional.Present(listOf(MediaSort.POPULARITY_DESC))
        )
        return apolloClient.query(query).execute().dataAssertNoErrors
    }

    override suspend fun getNextSeasonAnimeList(page: Int, perPage: Int): AnimeListQuery.Data {
        // TODO: Set parameters dynamically
        val query = AnimeListQuery(
            page = page,
            perPage = perPage,
            season = Optional.Present(MediaSeason.FALL),
            seasonYear = Optional.Present(2022),
            sort = Optional.Present(listOf(MediaSort.POPULARITY_DESC))
        )
        return apolloClient.query(query).execute().dataAssertNoErrors
    }

    override suspend fun getPopularAnimeList(page: Int, perPage: Int): AnimeListQuery.Data {
        val query = AnimeListQuery(
            page = page,
            perPage = perPage,
            season = Optional.Present(MediaSeason.FALL),
            seasonYear = Optional.Present(2022),
            sort = Optional.Present(listOf(MediaSort.POPULARITY_DESC))
        )
        return apolloClient.query(query).execute().dataAssertNoErrors
    }

    override suspend fun getTopAnimeList(page: Int, perPage: Int): AnimeListQuery.Data {
        val query = AnimeListQuery(
            page = page,
            perPage = perPage,
            sort = Optional.Present(listOf(MediaSort.SCORE_DESC))
        )
        return apolloClient.query(query).execute().dataAssertNoErrors
    }
}
