package com.github.watabee.anilist.core.model

data class AnimeList(
    val pageInfo: PageInfo,
    val mediaList: List<Media>
) {
    data class Media(
        val id: Int,
        val title: String?,
        val coverImageUrl: String?
    )
}
