package com.github.watabee.anilist.feature.animelist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.watabee.anilist.core.model.AnimeListType
import com.github.watabee.anilist.core.navigation.AnilistNavigation
import com.github.watabee.anilist.feature.animelist.AnimeListScreen

object AnimeListNavigation : AnilistNavigation {
    internal const val argAnimeListType = "type"
    override val route: String = "anime_list/{$argAnimeListType}"

    fun createNavRoute(animeListType: AnimeListType): String = "anime_list/${animeListType.name}"
}

fun NavGraphBuilder.animeListGraph() {
    composable(
        route = AnimeListNavigation.route,
        arguments = listOf(navArgument(AnimeListNavigation.argAnimeListType) { type = NavType.StringType })
    ) {
        AnimeListScreen()
    }
}
