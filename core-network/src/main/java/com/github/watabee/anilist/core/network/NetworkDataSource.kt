package com.github.watabee.anilist.core.network

interface NetworkDataSource {
    suspend fun getAnimeTop(): AnimeTopQuery.Data
}
