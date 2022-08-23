package com.github.watabee.anilist

sealed interface Screen {
    val route: String

    object AnimeTop : Screen {
        override val route: String = "anime_top"
    }
}
