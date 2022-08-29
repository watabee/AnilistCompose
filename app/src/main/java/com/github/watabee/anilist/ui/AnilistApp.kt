package com.github.watabee.anilist.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.github.watabee.anilist.core.designsystem.theme.AnilistTheme
import com.github.watabee.anilist.feature.animelist.navigation.AnimeListNavigation
import com.github.watabee.anilist.feature.animelist.navigation.animeListGraph
import com.github.watabee.anilist.feature.animetop.navigation.AnimeTopNavigation
import com.github.watabee.anilist.feature.animetop.navigation.animeTopGraph

@Composable
fun AnilistApp() {
    AnilistTheme {
        Scaffold {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = AnimeTopNavigation.route) {
                animeTopGraph(
                    onViewAllButtonClicked = {
                        navController.navigate(AnimeListNavigation.createNavRoute(it))
                    }
                )
                animeListGraph()
            }
        }
    }
}
