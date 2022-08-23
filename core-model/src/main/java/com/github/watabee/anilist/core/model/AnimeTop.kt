package com.github.watabee.anilist.core.model

data class AnimeTop(
    val trending: List<Media>,
    val season: List<Media>,
    val nextSeason: List<Media>,
    val popular: List<Media>,
    val top: List<Media>
) {
    data class Media(
        val id: Int,
        val title: String?,
        val coverImageUrl: String?,
        val format: String?,
        val genres: List<String>,
        val mainStudio: String?
    )
}
