package com.github.watabee.anilist.core.network.graphql

import com.apollographql.apollo3.ApolloClient
import com.github.watabee.anilist.core.network.AnimeTopQuery
import com.github.watabee.anilist.core.network.NetworkDataSource
import com.github.watabee.anilist.core.network.type.MediaSeason
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
}
