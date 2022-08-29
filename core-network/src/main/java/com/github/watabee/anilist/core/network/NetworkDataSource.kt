package com.github.watabee.anilist.core.network

interface NetworkDataSource {
    suspend fun getAnimeTop(): AnimeTopQuery.Data

    suspend fun getTrendingAnimeList(page: Int, perPage: Int): AnimeListQuery.Data

    suspend fun getCurrentSeasonAnimeList(page: Int, perPage: Int): AnimeListQuery.Data

    suspend fun getNextSeasonAnimeList(page: Int, perPage: Int): AnimeListQuery.Data

    suspend fun getPopularAnimeList(page: Int, perPage: Int): AnimeListQuery.Data

    suspend fun getTopAnimeList(page: Int, perPage: Int): AnimeListQuery.Data
}
