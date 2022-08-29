package com.github.watabee.anilist.feature.animetop.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.watabee.anilist.core.model.AnimeListType
import com.github.watabee.anilist.core.navigation.AnilistNavigation
import com.github.watabee.anilist.feature.animetop.AnimeTopScreen

object AnimeTopNavigation : AnilistNavigation {
    override val route: String = "anime_top"
}

fun NavGraphBuilder.animeTopGraph(onViewAllButtonClicked: (AnimeListType) -> Unit) {
    composable(route = AnimeTopNavigation.route) {
        AnimeTopScreen(onViewAllButtonClicked = onViewAllButtonClicked)
    }
}
